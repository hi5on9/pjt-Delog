
### is_hidden  =>    1. 전체공개   2. 회원만   3. 숨기기
### is_comment =>    1. 댓글 허용  2. 댓글 불가
### category   =>    1. 추후 예정

CREATE TABLE `NOTICE`(
	`Nid`	INT(10) AUTO_INCREMENT,
	`Title` VARCHAR(128) DEFAULT NULL,
	`Contents` VARCHAR(5000) DEFAULT NULL,
	`Create_date` DATETIME DEFAULT CURRENT_TIMESTAMP(),
	`is_hidden` INT(5) DEFAULT 1,
	`is_comment` INT(5) DEFAULT 1,
	`is_main`		BOOLEAN DEFAULT FALSE,
	`category`	INT(5)	DEFAULT 1,
	`hit`			INT(10)	DEFAULT 0,
	PRIMARY KEY(`Nid`)
)ENGINE=INNODB DEFAULT CHARSET=UTF8;


	
insert into NOTICE
			(title, contents, create_date, is_hidden, is_comment, is_main, category)
			VALUES ("Notice test", "This is notice test", now(), 1, 1, false, 1);
			
	
	insert into NOTICE
			(title, contents, create_date, is_hidden, is_comment, is_main, category)
			VALUES ("Notice test1", "This is notice test1", now(), 1, 1, false, 1);


insert into NOTICE
			(title, contents, create_date, is_hidden, is_comment, is_main, category)
			VALUES ("Notice test2", "This is notice test2", now(), 1, 1, false, 1);

insert into NOTICE
			(title, contents, create_date, is_hidden, is_comment, is_main, category)
			VALUES ("Notice test3", "This is notice test3", now(), 1, 1, false, 1);
				

insert into NOTICE
			(title, contents, create_date, is_hidden, is_comment, is_main, category)
			VALUES ("Notice main", "This is notice main", now(), 1, 1, TRUE, 1);

insert into NOTICE
			(title, contents, create_date, is_hidden, is_comment, is_main, category)
			VALUES ("Sample data", "This is sample data", now(), 1, 1, false, 1);
			
				

select *
		from NOTICE
		where is_hidden = 1
		order by is_main desc, Create_date desc



	