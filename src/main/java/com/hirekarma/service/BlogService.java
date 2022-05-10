package com.hirekarma.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hirekarma.beans.BlogBean;
import com.hirekarma.model.Blog;

@Service
public interface BlogService {
	public Blog addBlogByAdmin(BlogBean bean, MultipartFile file) throws ParseException, IOException, Exception;
	public Blog addBlogByCooperate(BlogBean bean, String token, MultipartFile file) throws ParseException, IOException, Exception;
	public Blog getBlogByAdminById(int blogId) throws Exception;
	public Blog getBlogByAdminBySlug(String slug) throws Exception;
	public List<Blog> getAllBlogByAdmin();
	public Blog getBlogByCoporateBySlug(String slug,String token) throws Exception;
	public List<Blog> getAllBlogsByCoporate(String token) throws Exception;
	public List<Blog> getAllBlogsByUser();
	public void deleteBlogBySlug(String token,String slug) throws Exception ;
	public Blog updateBlogByAdmin(BlogBean bean,String slug,MultipartFile file) throws IOException ;
	public Blog updateBlogByCorporate(String token, BlogBean bean,String slug,MultipartFile file) throws Exception;
	public void activateBlogByAdmin(String slug);
	public void deleteAdminBlogBySlug(String slug) throws Exception ;
	
}
