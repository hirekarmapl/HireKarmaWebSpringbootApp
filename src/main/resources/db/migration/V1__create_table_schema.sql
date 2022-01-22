CREATE TABLE internship (
  internship_id bigint(20) NOT NULL AUTO_INCREMENT,
  about varchar(255) DEFAULT NULL,
  city varchar(255) DEFAULT NULL,
  created_on datetime(6) DEFAULT NULL,
  delete_status varchar(255) DEFAULT NULL,
  description varchar(255) DEFAULT NULL,
  description_file longblob,
  internship_title varchar(255) DEFAULT NULL,
  internship_type varchar(255) DEFAULT NULL,
  openings int(11) DEFAULT NULL,
  salary double DEFAULT NULL,
  skills varchar(255) DEFAULT NULL,
  status varchar(255) DEFAULT NULL,
  updated_on datetime(6) DEFAULT NULL,
  user_id bigint(20) DEFAULT NULL,
  PRIMARY KEY (internship_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE internship_apply (
  internship_apply_id bigint(20) NOT NULL AUTO_INCREMENT,
  cover_letter varchar(255) DEFAULT NULL,
  created_on datetime(6) DEFAULT NULL,
  delete_status varchar(255) DEFAULT NULL,
  earliest_joining_date varchar(255) DEFAULT NULL,
  hire_reason varchar(255) DEFAULT NULL,
  updated_on datetime(6) DEFAULT NULL,
  user_id bigint(20) DEFAULT NULL,
  PRIMARY KEY (internship_apply_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE job (
  job_id bigint(20) NOT NULL AUTO_INCREMENT,
  about varchar(255) DEFAULT NULL,
  category varchar(255) DEFAULT NULL,
  city varchar(255) DEFAULT NULL,
  created_on datetime(6) DEFAULT NULL,
  delete_status varchar(255) DEFAULT NULL,
  description varchar(255) DEFAULT NULL,
  description_file longblob,
  job_title varchar(255) DEFAULT NULL,
  jobt_ype varchar(255) DEFAULT NULL,
  openings int(11) DEFAULT NULL,
  salary double DEFAULT NULL,
  skills varchar(255) DEFAULT NULL,
  status bit(1) DEFAULT NULL,
  updated_on datetime(6) DEFAULT NULL,
  user_id bigint(20) DEFAULT NULL,
  wfh_check_box bit(1) DEFAULT NULL,
  PRIMARY KEY (job_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

CREATE TABLE job_apply (
  job_apply_id bigint(20) NOT NULL AUTO_INCREMENT,
  cover_letter varchar(255) DEFAULT NULL,
  created_on datetime(6) DEFAULT NULL,
  delete_status varchar(255) DEFAULT NULL,
  earliest_joining_date varchar(255) DEFAULT NULL,
  hire_reason varchar(255) DEFAULT NULL,
  updated_on datetime(6) DEFAULT NULL,
  user_id bigint(20) DEFAULT NULL,
  PRIMARY KEY (job_apply_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE organization (
  organization_id bigint(20) NOT NULL AUTO_INCREMENT,
  cin_gst_num varchar(255) DEFAULT NULL,
  created_on datetime(6) DEFAULT NULL,
  description varchar(255) DEFAULT NULL,
  logo longblob,
  org_email varchar(255) DEFAULT NULL,
  org_name varchar(255) DEFAULT NULL,
  status varchar(255) DEFAULT NULL,
  updated_on datetime(6) DEFAULT NULL,
  user_id bigint(20) DEFAULT NULL,
  PRIMARY KEY (organization_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE student_edu_details (
  student_edu_det_id bigint(20) NOT NULL AUTO_INCREMENT,
  additional_info varchar(255) DEFAULT NULL,
  address varchar(255) DEFAULT NULL,
  created_on datetime(6) DEFAULT NULL,
  delete_status varchar(255) DEFAULT NULL,
  examination varchar(255) DEFAULT NULL,
  institute_name varchar(255) DEFAULT NULL,
  marks double DEFAULT NULL,
  updated_on datetime(6) DEFAULT NULL,
  user_id bigint(20) DEFAULT NULL,
  year_of_passing int(11) DEFAULT NULL,
  PRIMARY KEY (student_edu_det_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE student_prof_exp (
  student_prof_exp_id bigint(20) NOT NULL AUTO_INCREMENT,
  company_address varchar(255) DEFAULT NULL,
  company_name varchar(255) DEFAULT NULL,
  created_on datetime(6) DEFAULT NULL,
  delete_status varchar(255) DEFAULT NULL,
  description varchar(255) DEFAULT NULL,
  end_date date DEFAULT NULL,
  position varchar(255) DEFAULT NULL,
  skills varchar(255) DEFAULT NULL,
  start_date date DEFAULT NULL,
  updated_on datetime(6) DEFAULT NULL,
  user_id bigint(20) DEFAULT NULL,
  PRIMARY KEY (student_prof_exp_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE user_profile (
  user_id bigint(20) NOT NULL AUTO_INCREMENT,
  university_address varchar(255) DEFAULT NULL,
  auth_provider varchar(255) DEFAULT NULL,
  created_on datetime(6) DEFAULT NULL,
  email varchar(255) DEFAULT NULL,
  image longblob,
  name varchar(255) DEFAULT NULL,
  password varchar(255) DEFAULT NULL,
  phone_no varchar(255) DEFAULT NULL,
  status varchar(255) DEFAULT NULL,
  university_email_address varchar(255) DEFAULT NULL,
  updated_on datetime(6) DEFAULT NULL,
  user_type varchar(255) DEFAULT NULL,
  PRIMARY KEY (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE share_job_to_university (
  share_job_id bigint(20) NOT NULL AUTO_INCREMENT,
  created_by varchar(255) DEFAULT NULL,
  created_on datetime(6) DEFAULT NULL,
  job_id bigint(20) DEFAULT NULL,
  job_status varchar(255) DEFAULT NULL,
  rejection_feedback varchar(255) DEFAULT NULL,
  university_id bigint(20) DEFAULT NULL,
  university_response_status bit(1) DEFAULT NULL,
  updated_by varchar(255) DEFAULT NULL,
  updated_on datetime(6) DEFAULT NULL,
  PRIMARY KEY (share_job_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

CREATE TABLE `university_job_share` (
  `share_job_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_by` varchar(255) DEFAULT NULL,
  `created_on` datetime(6) DEFAULT NULL,
  `job_id` bigint(20) DEFAULT NULL,
  `student_id` bigint(20) DEFAULT NULL,
  `university_id` bigint(20) DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `updated_on` datetime(6) DEFAULT NULL,
  `job_status` varchar(255) DEFAULT NULL,
  `response_feedback` varchar(255) DEFAULT NULL,
  `student_response_status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`share_job_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;