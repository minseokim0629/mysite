package mysite.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class BoardVo {
	private Long id;
	private String title;
	private String content;
	private Long hit;
	private String regDate;
	private Long gNo;
	private Long oNo;
	private Long depth;
	private String type;
	private Long userId;
	private String userName;
}