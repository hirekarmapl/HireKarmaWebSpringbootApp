package com.hirekarma.serviceimpl;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.json.simple.parser.ParseException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hirekarma.beans.BlogBean;
import com.hirekarma.model.Blog;
import com.hirekarma.model.Category;
import com.hirekarma.model.Corporate;
import com.hirekarma.repository.BlogRepository;
import com.hirekarma.repository.CategoryRepository;
import com.hirekarma.repository.CorporateRepository;
import com.hirekarma.service.BlogService;
import com.hirekarma.utilty.Utility;
import com.hirekarma.utilty.Validation;

@Service("BlogService")
public class BlogServiceImpl implements BlogService {

	@Autowired
	CorporateRepository corporateRepository;

	@Autowired
	BlogRepository blogRepository;

	@Autowired
	CategoryRepository categoryRepository;

//	--------------add functions---------------

	public Blog addBlogByAdmin(BlogBean bean, MultipartFile file) throws ParseException, IOException {

		byte[] arr = Utility.readFile(file);

		Blog blog = new Blog();
		BeanUtils.copyProperties(bean, blog);

		blog.setSlug(Utility.createSlug(bean.getTitle()));
		blog.setImage(arr);
		blog.setCorporate(null);
		blog.setIspublic(true);
		return this.blogRepository.save(blog);

	}

	public Blog addBlogByCooperate(BlogBean bean, String token, MultipartFile file) throws ParseException, IOException {

		String email = Validation.validateToken(token);
		Corporate corporate = corporateRepository.findByEmail(email);
		Category category = categoryRepository.getById(bean.getCategoryid());

		byte[] arr = Utility.readFile(file);

		Blog blog = new Blog();
		BeanUtils.copyProperties(bean, blog);

		blog.setSlug(Utility.createSlug(bean.getTitle()));
		blog.setImage(arr);
		blog.setCorporate(corporate);
		blog.setIspublic(false);
		return this.blogRepository.save(blog);

	}

//	-------------- get functions --------------
	
	public Blog getBlogByAdminById(int blogId) {
		return this.blogRepository.getById(blogId);
	}
	
	public Blog getBlogByAdminBySlug(String slug) {
		return this.blogRepository.findBySlug(slug);
	}

	public List<Blog> getAllBlogByAdmin(){
		return this.blogRepository.findAll();
	}
	
//	get a blog from slUg post by specific cooperate
	public Blog getBlogByCoporateBySlug(String slug,String token) throws Exception {
		String email = Validation.validateToken(token);
		Corporate corporate = corporateRepository.findByEmail(email);
		Blog blog = blogRepository.findBySlug(slug);
		if(corporate.getCorporateId()!=blog.getCorporate().getCorporateId()) {
			throw new Exception("unauthorized");
		}
		return blog;
		
	}
	
//	get all blog posted by cooperate
	public List<Blog> getAllBlogsByCoporate(String token) throws Exception{
		String email = Validation.validateToken(token);
		Corporate corporate = corporateRepository.findByEmail(email);
		return this.blogRepository.findByCorporate(corporate);
		
	}
	
//	get all the blog  which are active
	public List<Blog> getAllBlogsByUser(){
		return this.blogRepository.findByIspublicTrue();
	}
	
//	------------------- delete by slug
	
	public void deleteBlogBySlug(String token,String slug) throws Exception  {
		String email = Validation.validateToken(token);
		Corporate corporate = corporateRepository.findByEmail(email);
		Blog blog = blogRepository.findBySlug(slug);
		if(corporate==null||blog==null) {
			throw new Exception("unauthorized");
		}
		
		if(corporate.getCorporateId()!=blog.getCorporate().getCorporateId()) {
			throw new Exception("unauthorized");
		}
		this.blogRepository.deleteById(blog.getId());
	}
	
	
//	------------------ update by slug ----------
	
	public Blog updateBlogByAdmin(BlogBean bean,String slug,MultipartFile file) throws IOException {
		
		byte[] arr = Utility.readFile(file);

		Blog blog = this.blogRepository.findBySlug(slug);
		int id = blog.getId();
		BeanUtils.copyProperties(bean, blog);

		blog.setId(id);
		blog.setSlug(Utility.createSlug(bean.getTitle()));
		blog.setImage(arr);
		blog.setCorporate(null);
		blog.setIspublic(true);
		return this.blogRepository.save(blog);
	}
	
	public Blog updateBlogByCorporate(String token, BlogBean bean,String slug,MultipartFile file) throws Exception {
		String email = Validation.validateToken(token);
		Corporate corporate = corporateRepository.findByEmail(email);
		Blog blog = blogRepository.findBySlug(slug);
		if(corporate.getCorporateId()!=blog.getCorporate().getCorporateId()) {
			throw new Exception("unauthorized");
		}
		
		byte[] arr = Utility.readFile(file);
		
		int id = blog.getId();
		BeanUtils.copyProperties(bean, blog);

		blog.setId(id);
		blog.setSlug(Utility.createSlug(bean.getTitle()));
		blog.setImage(arr);
		blog.setCorporate(null);
		blog.setIspublic(true);
		return this.blogRepository.save(blog);
	}
	
	// --------------------- activate slug----------------
	public void activateBlogByAdmin(String slug) {
		Blog blog = this.blogRepository.findBySlug(slug);
		blog.setIspublic(true);

	}

	
}




//================================= roadmap for this service==========================
//add blogs - 
// get type:
//	if (cooperate)
//	then
//		set cooperate = cooperate
//	else
//		set cooperat null
//update 
//- only the person creating the api can update
//
//delete
//- only created person can delete the slug
//
//blog/activate
//to set ispublic true
//
//
//
//get blog
//--
//cooperate/blog - get all teh blog posted by coooperate
//admin/blog - get all the blog posted by everyone
///blog - if ispublic is true then get the blog
