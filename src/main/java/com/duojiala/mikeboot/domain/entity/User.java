package com.duojiala.mikeboot.domain.entity;

import javax.persistence.*;

@Table(name = "user")
public class User {
    /**
     * 主键id
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 生日(yyyy-MM-dd)
     */
    private String birthday;

    /**
     * 性别：0:女；1:男
     */
    private Byte gender;

    /**
     * 获取主键id
     *
     * @return id - 主键id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置主键id
     *
     * @param id 主键id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取姓名
     *
     * @return name - 姓名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置姓名
     *
     * @param name 姓名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取生日(yyyy-MM-dd)
     *
     * @return birthday - 生日(yyyy-MM-dd)
     */
    public String getBirthday() {
        return birthday;
    }

    /**
     * 设置生日(yyyy-MM-dd)
     *
     * @param birthday 生日(yyyy-MM-dd)
     */
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    /**
     * 获取性别：0:女；1:男
     *
     * @return gender - 性别：0:女；1:男
     */
    public Byte getGender() {
        return gender;
    }

    /**
     * 设置性别：0:女；1:男
     *
     * @param gender 性别：0:女；1:男
     */
    public void setGender(Byte gender) {
        this.gender = gender;
    }
}