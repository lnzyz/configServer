package com.configserver.dao;

import com.configserver.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

public interface UserDAO {

    @Insert("insert into user (username, password, createtime, type, minBonusOdds, fandian, shangji, parentList) values (#{username}, #{password}, now(), #{type}, #{minBonusOdds}, #{fandian}, #{shangji}, #{parentList})")
    @SelectKey(statement = "select last_insert_id()", keyProperty = "id", before = false, resultType = int.class)
    int insert(User user);

    @Update("<script> " +
            "update user " +
            "<trim prefix=\"set\" suffixOverrides=\",\"> " +
            "<if test=\"password != null\" >password=#{password},</if>" +
            "<if test=\"drawFlag != null\" >drawFlag=#{drawFlag},</if>" +
            "<if test=\"ip != null\" >ip=#{ip},</if>" +
            "<if test=\"status != null\" >status=#{status},</if>" +
            "<if test=\"money != null\" >money=#{money},</if>" +
            "<if test=\"touzhuFlag != null\" >touzhuFlag=#{touzhuFlag},</if>" +
            "<if test=\"time != null\" >time=#{time},</if>" +
            "<if test=\"online != null\" >online=#{online},</if>" +
            "<if test=\"drawPwd != null\" >drawPwd=#{drawPwd},</if>" +
            "<if test=\"qsType1 != null\" >qsType1=#{qsType1},</if>" +
            "<if test=\"qsType2 != null\" >qsType2=#{qsType2},</if>" +
            "<if test=\"answer1 != null\" >answer1=#{answer1},</if>" +
            "<if test=\"answer2 != null\" >answer2=#{answer2},</if>" +
            "<if test=\"fenhong != null\" >fenhong=#{fenhong},</if>" +
            "<if test=\"name != null\" >name=#{name},</if>" +
            "<if test=\"time != null\" >time=now(),</if>" +
            "<if test=\"message != null\" >message=#{message},</if>" +
            "<if test=\"nickName != null\" >nickName=#{nickName},</if>" +
            "</trim>" +
            " where id=#{id} " +
            " </script> ")
    @SelectKey(statement = "select last_insert_id()", keyProperty = "id", before = false, resultType = int.class)
    int update(User user);

    @Update("<script> " +
            "update user " +
            "<trim prefix=\"set\" suffixOverrides=\",\"> " +
            "<if test=\"money > 0\" >money=money + #{money},</if>" +
            "</trim>" +
            " where username=#{user.username} " +
            " </script> ")
    int updateMoney(@Param("user") User user, @Param("money") double money);

    @Update("<script> " +
            "update user " +
            "<trim prefix=\"set\" suffixOverrides=\",\"> " +
            "<if test=\"money > 0\" >money=money - #{money},</if>" +
            "</trim>" +
            " where username=#{user.username} " +
            " </script> ")
    int kouqian(@Param("user") User user, @Param("money") double money);

    @Update("<script> " +
            "update user " +
            "<trim prefix=\"set\" suffixOverrides=\",\"> " +
            "<if test=\"fandian != null\" >fandian=#{fandian},</if>" +
            "</trim>" +
            " where username=#{username} " +
            " </script> ")
    int updagteFandian(@Param("username") String username, @Param("fandian") Double fandian);

    @Delete("delete from user where id=#{id}")
    @SelectKey(statement = "select last_insert_id()", keyProperty = "id", before = false, resultType = int.class)
    int delete(int id);

    @Select("<script> " +
            "select * " +
            "from user" +
            " <trim prefix=\"where\" prefixOverrides=\"AND |OR \">" +
            " <if test=\"user.id != null\"> AND id=#{user.id}</if> " +
            " <if test=\"user.username != null\"> AND username=#{user.username}</if> " +
            " <if test=\"user.name != null\"> AND name=#{user.name}</if> " +
            " <if test=\"user.online != null\"> AND online=#{user.online}</if> " +
            " <if test=\"user.type != null\"> AND type=#{user.type}</if> " +
            " <if test=\"user.nickName != null\"> AND nickName=#{user.nickName}</if> " +
            " <if test=\"user.shangji != null\"> AND shangji=#{user.shangji}</if> " +
            " <if test=\"user.parentList != null\"> AND parentList like #{user.parentList}</if> " +
            " <if test=\"startTime != null\"><![CDATA[  AND createTime >=  DATE_FORMAT(#{startTime}, '%Y-%m-%d %H:%T:%s') AND createTime <= DATE_FORMAT(#{endTime}, '%Y-%m-%d %H:%T:%s')]]></if>" +
            " <if test=\"beginAmount != null\"> AND<![CDATA[ money>#{beginAmount} AND money<${endAmount} ]]></if> " +
            " </trim> " +
            " order by id desc" +
            " <if test=\"from != null\"> limit #{from},#{limit} </if> " +
            " </script> ")
    List<User> select(@Param("user") User user, @Param("from") Integer from, @Param("limit") Integer limit, @Param("startTime") Date startTime, @Param("endTime") Date endTime, @Param("beginAmount") Double beginAmount, @Param("endAmount") Double endAmount);

    @Select("<script> " +
            "select count(*) " +
            "from user" +
            " <trim prefix=\"where\" prefixOverrides=\"AND |OR \">" +
            " <if test=\"user.id != null\"> AND id=#{user.id}</if> " +
            " <if test=\"user.username != null\"> AND username=#{user.username}</if> " +
            " <if test=\"user.name != null\"> AND name=#{user.name}</if> " +
            " <if test=\"user.online != null\"> AND online=#{user.online}</if> " +
            " <if test=\"user.type != null\"> AND type=#{user.type}</if> " +
            " <if test=\"user.nickName != null\"> AND nickName=#{user.nickName}</if> " +
            " <if test=\"user.shangji != null\"> AND shangji=#{user.shangji}</if> " +
            " <if test=\"user.parentList != null\"> AND parentList like #{user.parentList}</if> " +
            " <if test=\"startTime != null\"><![CDATA[  AND createTime >=  DATE_FORMAT(#{startTime}, '%Y-%m-%d %H:%T:%s') AND createTime <= DATE_FORMAT(#{endTime}, '%Y-%m-%d %H:%T:%s')]]></if>" +
            " <if test=\"beginAmount != null\"> AND<![CDATA[ money>#{beginAmount} AND money<${endAmount} ]]></if> " +
            " </trim> " +
            " </script> ")
    int count(@Param("user") User user, @Param("from") Integer from, @Param("limit") Integer limit, @Param("startTime") Date startTime, @Param("endTime") Date endTime, @Param("beginAmount") Double beginAmount, @Param("endAmount") Double endAmount);

    @Select("select sum(money) from user where parentList like #{account }")
    Double sum(@Param("account") String account);
}