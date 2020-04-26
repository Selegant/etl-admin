package com.selegant.kettle.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.selegant.kettle.model.KettleRepository;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface KettleRepositoryMapper extends BaseMapper<KettleRepository> {

    /**
     * 清空所有资源
     */
    void truncateRecord();

    /**
     * 修改其他资源库的使用标志
     * @param id
     * @return
     */
    int updateOtherUseFlag(@Param(value = "id") int id);

    /**
     * 修改其他资源库的使用标志
     * @return
     */
    KettleRepository getUsedKettleRepository();
}
