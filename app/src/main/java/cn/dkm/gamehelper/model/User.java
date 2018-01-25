package cn.dkm.gamehelper.model;

import com.ab.db.orm.annotation.Column;
import com.ab.db.orm.annotation.Id;
import com.ab.db.orm.annotation.Table;

import lombok.Data;

@Table(name = "user")
@Data
public class User {

	// ID @Id主键,int类型,数据库建表时此字段会设为自增长
	@Id
	@Column(name = "_id")
	private int _id;

	@Column(name = "u_id")
	private String uId;

	// 登录用户名 length=20数据字段的长度是20
	@Column(name = "user_name", length = 20)
	private String userName;

	// 用户密码
	@Column(name = "password")
	private String password;

	// 昵称
	@Column(name = "nick_name")
	public String nickName;

	// 年龄一般是数值,用type = "INTEGER"规范一下.
	@Column(name = "age", type = "INTEGER")
	private int age;

	// 用户性别
	@Column(name = "sex")
	public String sex;

	// 用户邮箱
	// 假设您开始时没有此属性,程序开发中才想到此属性,也不用卸载程序.
	@Column(name = "email")
	private String email;

	// 头像地址
	@Column(name = "head_url")
	private String headUrl;

	// 创建时间
	@Column(name = "create_time")
	private String createTime;

	// 城市
	@Column(name = "city")
	private String city;

	// 简介
	@Column(name = "intro")
	private String intro;

	// 积分
	@Column(name = "point")
	private int point;

	// 用户权限,0表示管理员，1表示会员
	@Column(name = "rights")
	public int rights;

	// 用户问题
	@Column(name = "question")
	public String question;

	// 用户答案
	@Column(name = "answer")
	public String answer;

	// 登录次数
	@Column(name = "login_count")
	public int loginCount;

	// 有些字段您可能不希望保存到数据库中,不用@Column注释就不会映射到数据库.
	private String remark;

	// 登录授权
	@Column(name = "access_token")
	private String accessToken;

	// 是否为当前登录
	@Column(name = "is_login_user")
	private boolean isLoginUser;



}
