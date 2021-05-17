
### 게시글에 댓글일 경우 : Pid : pId / Prid : 0
### 댓글에 댓글일 경우   : Pid : pId / Prid : rId
### order type 			 : 1- likes순  2- 최신댓글순  3-공감비율순


CREATE TABLE `REPLY`(
	`Rid`	INT(10) AUTO_INCREMENT,
	`Pid` INT(10),
	`Prid` INT(10) DEFAULT 0,
	`writer` INT(10),
	`contents` VARCHAR(5000) DEFAULT NULL,
	`create_date` DATETIME DEFAULT CURRENT_TIMESTAMP(),
	`likes`		INT(10) DEFAULT 0,
	`hates`		INT(10)	DEFAULT 0,
	PRIMARY KEY(`Rid`),
	FOREIGN KEY(`Pid`) REFERENCES POST(`Pid`) ON DELETE cascade,
	FOREIGN KEY(`writer`) REFERENCES USER(`uid`)
)ENGINE=INNODB DEFAULT CHARSET=UTF8;



insert into REPLY
		(pid, prid, writer, contents, create_date, likes, hates)
		values ( 1, 0, 2, "1번글에 대한 댓글입니다.", now(), 0, 0);

insert into REPLY
		(pid, prid, writer, contents, create_date, likes, hates)
		VALUES ( 1, 1, 4, "댓글에 대한 댓글입니다.", now(), 0, 0);


insert into REPLY
		(pid, prid, writer, contents, create_date, likes, hates)
		values ( 1, 0, 5, "1번글에 대한 또다른 댓글입니다.", now(), 0, 0);

insert into REPLY
		(pid, prid, writer, contents, create_date, likes, hates)
		VALUES ( 1, 3, 5, "6번 댓글에 대한 댓글입니다.", now(), 0, 0);


insert into REPLY
		(pid, prid, writer, contents, create_date, likes, hates)
		VALUES ( 1, 3, 2, "6번 댓글에 대한 또다른 댓글입니다.", now(), 0, 0);


insert into REPLY
		(pid, prid, writer, contents, create_date, likes, hates)
		values ( 1, 0, 7, "1번글에 대한 또다른 댓글입니다.", NOW(), 10, 0);



SELECT * FROM REPLY;
SELECT * FROM USER;


UPDATE REPLY
SET
	order_type = 2
	WHERE pid = 1;
	

SELECT r.rid, r.pid, r.Prid, u.Nickname, r.`contents`, r.create_date, r.likes, r.hates
FROM REPLY r join USER u
on r.writer = u.uid
WHERE r.Pid = 1 AND prid = 0
ORDER BY (
	case 3
			when 1 then r.likes 
			when 2 then r.create_date
			when 3 then r.likes / (r.hates+1)
	END) desc
;

	

SELECT r.rid, r.pid, r.Prid, u.Nickname, r.`contents`, r.create_date, r.likes, r.hates
FROM REPLY r join USER u
on r.writer = u.uid
WHERE r.Pid = 1 AND prid = 6;

