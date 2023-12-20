package com.bjtu.dao;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SimilarityDao {

    public void addOne(Integer contentID1,Integer contentID2,double similar);

}
