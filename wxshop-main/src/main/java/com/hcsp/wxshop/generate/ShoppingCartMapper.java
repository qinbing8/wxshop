package com.hcsp.wxshop.generate;

import com.hcsp.wxshop.generate.ShoppingCart;
import com.hcsp.wxshop.generate.ShoppingCartExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ShoppingCartMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SHOPPING_CART
     *
     * @mbg.generated Mon Jan 17 22:26:47 CST 2022
     */
    long countByExample(ShoppingCartExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SHOPPING_CART
     *
     * @mbg.generated Mon Jan 17 22:26:47 CST 2022
     */
    int deleteByExample(ShoppingCartExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SHOPPING_CART
     *
     * @mbg.generated Mon Jan 17 22:26:47 CST 2022
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SHOPPING_CART
     *
     * @mbg.generated Mon Jan 17 22:26:47 CST 2022
     */
    int insert(ShoppingCart record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SHOPPING_CART
     *
     * @mbg.generated Mon Jan 17 22:26:47 CST 2022
     */
    int insertSelective(ShoppingCart record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SHOPPING_CART
     *
     * @mbg.generated Mon Jan 17 22:26:47 CST 2022
     */
    List<ShoppingCart> selectByExample(ShoppingCartExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SHOPPING_CART
     *
     * @mbg.generated Mon Jan 17 22:26:47 CST 2022
     */
    ShoppingCart selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SHOPPING_CART
     *
     * @mbg.generated Mon Jan 17 22:26:47 CST 2022
     */
    int updateByExampleSelective(@Param("record") ShoppingCart record, @Param("example") ShoppingCartExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SHOPPING_CART
     *
     * @mbg.generated Mon Jan 17 22:26:47 CST 2022
     */
    int updateByExample(@Param("record") ShoppingCart record, @Param("example") ShoppingCartExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SHOPPING_CART
     *
     * @mbg.generated Mon Jan 17 22:26:47 CST 2022
     */
    int updateByPrimaryKeySelective(ShoppingCart record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SHOPPING_CART
     *
     * @mbg.generated Mon Jan 17 22:26:47 CST 2022
     */
    int updateByPrimaryKey(ShoppingCart record);
}