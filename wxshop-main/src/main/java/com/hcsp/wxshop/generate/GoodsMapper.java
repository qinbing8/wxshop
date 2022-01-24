package com.hcsp.wxshop.generate;

import com.hcsp.wxshop.generate.Goods;
import com.hcsp.wxshop.generate.GoodsExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface GoodsMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table GOODS
     *
     * @mbg.generated Sun Jan 23 17:19:32 CST 2022
     */
    long countByExample(GoodsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table GOODS
     *
     * @mbg.generated Sun Jan 23 17:19:32 CST 2022
     */
    int deleteByExample(GoodsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table GOODS
     *
     * @mbg.generated Sun Jan 23 17:19:32 CST 2022
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table GOODS
     *
     * @mbg.generated Sun Jan 23 17:19:32 CST 2022
     */
    int insert(Goods record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table GOODS
     *
     * @mbg.generated Sun Jan 23 17:19:32 CST 2022
     */
    int insertSelective(Goods record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table GOODS
     *
     * @mbg.generated Sun Jan 23 17:19:32 CST 2022
     */
    List<Goods> selectByExampleWithBLOBs(GoodsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table GOODS
     *
     * @mbg.generated Sun Jan 23 17:19:32 CST 2022
     */
    List<Goods> selectByExample(GoodsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table GOODS
     *
     * @mbg.generated Sun Jan 23 17:19:32 CST 2022
     */
    Goods selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table GOODS
     *
     * @mbg.generated Sun Jan 23 17:19:32 CST 2022
     */
    int updateByExampleSelective(@Param("record") Goods record, @Param("example") GoodsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table GOODS
     *
     * @mbg.generated Sun Jan 23 17:19:32 CST 2022
     */
    int updateByExampleWithBLOBs(@Param("record") Goods record, @Param("example") GoodsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table GOODS
     *
     * @mbg.generated Sun Jan 23 17:19:32 CST 2022
     */
    int updateByExample(@Param("record") Goods record, @Param("example") GoodsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table GOODS
     *
     * @mbg.generated Sun Jan 23 17:19:32 CST 2022
     */
    int updateByPrimaryKeySelective(Goods record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table GOODS
     *
     * @mbg.generated Sun Jan 23 17:19:32 CST 2022
     */
    int updateByPrimaryKeyWithBLOBs(Goods record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table GOODS
     *
     * @mbg.generated Sun Jan 23 17:19:32 CST 2022
     */
    int updateByPrimaryKey(Goods record);
}