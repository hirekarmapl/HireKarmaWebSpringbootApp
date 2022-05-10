package com.hirekarma.serviceimpl;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

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
	
	@Autowired
	AWSS3Service awss3Service;

//	--------------add functions---------------

	public Blog addBlogByAdmin(BlogBean bean, MultipartFile file) throws ParseException, IOException,Exception {


		Blog blog = new Blog();
		BeanUtils.copyProperties(bean, blog);

		blog.setSlug(Utility.createSlug(bean.getTitle()));
		String url = null;
		try {
			if(!file.isEmpty()) {
				url = awss3Service.uploadFile(file);
			}
		}catch(Exception e) {
			
			url= null;
		}
		Category category = null;
		if(bean.getCategoryid()!=null) {
			Optional<Category> categoryOptional = this.categoryRepository.findById(bean.getCategoryid());
			if(!categoryOptional.isPresent()) {
				throw new Exception("invalid category id");
			}
			category = categoryOptional.get();
		}
		blog.setCategory(category);
		blog.setImageUrl(url);
		blog.setCorporate(null);
		blog.setIspublic(true);
		blog.setCreatedOn(LocalDateTime.now(ZoneId.of("Asia/Kolkata")));
		blog.setUpdatedOn(LocalDateTime.now(ZoneId.of("Asia/Kolkata")));
		return this.blogRepository.save(blog);

	}

	public Blog addBlogByCooperate(BlogBean bean, String token, MultipartFile file) throws ParseException, IOException,Exception {

		String email = Validation.validateToken(token);
		Corporate corporate = corporateRepository.findByEmail(email);


		Blog blog = new Blog();
		BeanUtils.copyProperties(bean, blog);

		blog.setSlug(Utility.createSlug(bean.getTitle()));
		String url = null;
		try {
			if(!file.isEmpty()) {
				url = awss3Service.uploadFile(file);
			}
			
		}catch(Exception e) {
			
			url= null;
		}
		Category category = null;
		if(bean.getCategoryid()!=null) {
			Optional<Category> categoryOptional = this.categoryRepository.findById(bean.getCategoryid());
			if(!categoryOptional.isPresent()) {
				throw new Exception("invalid category id");
			}
			category = categoryOptional.get();
		}
		blog.setCategory(category);
		blog.setImageUrl(url);
		blog.setCorporate(corporate);
		blog.setIspublic(true);
		blog.setCreatedOn(LocalDateTime.now(ZoneId.of("Asia/Kolkata")));
		blog.setUpdatedOn(LocalDateTime.now(ZoneId.of("Asia/Kolkata")));
		return this.blogRepository.save(blog);

	}

//	-------------- get functions --------------
	
	public Blog getBlogByAdminById(int blogId) throws Exception {
		Optional<Blog> optionalBlog = this.blogRepository.findById(blogId);
		if(!optionalBlog.isPresent()) {
			throw new Exception("invalid blog id");
		}
		return this.blogRepository.getById(blogId);
	}
	
	public Blog getBlogByAdminBySlug(String slug) throws Exception {
		Blog blog = this.blogRepository.findBySlug(slug);
		if(blog==null) {
			throw new Exception("invalid slug");
		}
		return blog;
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
		Blog blog = blogRepository.findBySlug(slug.trim());
		System.out.println(blog.getTitle());
		System.out.println(corporate.getCorporateId());
		if(corporate==null||blog==null) {
			
			System.out.println("inside coprorate blog null");
			throw new Exception("unauthorized");
		}
		
		if(blog.getCorporate()==null|| corporate.getCorporateId().compareTo(blog.getCorporate().getCorporateId())!=0) {
			throw new Exception("unauthorized");
		}
		this.blogRepository.deleteById(blog.getId());
	}
	
	
//	------------------ update by slug ----------
	
	public Blog updateBlogByAdmin(BlogBean bean,String slug,MultipartFile file) throws IOException {
		

		Blog blog = this.blogRepository.findBySlug(slug);
		int id = blog.getId();
		BeanUtils.copyProperties(bean, blog);

		blog.setId(id);
		blog.setSlug(Utility.createSlug(bean.getTitle()));
		String url = null;
		try {
			if(!file.isEmpty()) {
				url = awss3Service.uploadFile(file);
			}
		}catch(Exception e) {
			
			url= null;
		}
		blog.setImageUrl(url);
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
		
		
		int id = blog.getId();
		BeanUtils.copyProperties(bean, blog);

		blog.setId(id);
		blog.setSlug(Utility.createSlug(bean.getTitle()));
		String url = null;
		try {

			 url = awss3Service.uploadFile(file);
		}catch(Exception e) {
			
			url= null;
		}
		blog.setImageUrl(url);
		blog.setCorporate(null);
		blog.setIspublic(true);
		return this.blogRepository.save(blog);
	}
	
	// --------------------- activate slug----------------
	public void activateBlogByAdmin(String slug) {
		Blog blog = this.blogRepository.findBySlug(slug);
		blog.setIspublic(true);
	}

	public void deleteAdminBlogBySlug(String slug) throws Exception  {
		
		Blog blog = blogRepository.findBySlug(slug.trim());
		System.out.println(blog.getTitle());
		if(blog.getCorporate()!=null||blog==null) {
			
			System.out.println("inside coprorate blog null");
			throw new Exception("unauthorized");
		}
		
		this.blogRepository.deleteById(blog.getId());
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
