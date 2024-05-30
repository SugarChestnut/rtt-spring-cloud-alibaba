package cn.rentaotao.user.pojo;

import com.baomidou.mybatisplus.annotation.TableName;

/**
 * @author rtt
 * @date 2023/9/5 15:16
 */
@TableName("i_user")
public class User {

    private Integer id;

    private Integer age;

    private String username;

    private Integer account;

    public User() {
    }

    public User(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "IUser{" +
                "id=" + id +
                ", age=" + age +
                ", username='" + username + '\'' +
                ", account=" + account +
                '}';
    }

    public Integer getAccount() {
        return account;
    }

    public void setAccount(Integer account) {
        this.account = account;
    }

}
