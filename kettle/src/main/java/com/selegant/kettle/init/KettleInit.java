package com.selegant.kettle.init;

import com.selegant.kettle.mapper.KettleRepositoryMapper;
import com.selegant.kettle.model.KettleRepository;
import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.repository.kdr.KettleDatabaseRepository;
import org.pentaho.di.repository.kdr.KettleDatabaseRepositoryMeta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class KettleInit  {

    @Value("${kettle.plugins-path}")
    private String pluginsPath;

    @Autowired
    private KettleRepositoryMapper kettleRepositoryMapper;

    private Logger logger = LoggerFactory.getLogger(KettleInit.class);

    public KettleDatabaseRepository loadKettleDatabaseRepository() throws KettleException {
        logger.info(">>>>>>>>>>>系统启动初始化Kettle环境开始<<<<<<<<<<<");
        if(KettleEnvironment.isInitialized()){
            logger.info(">>>>>>>>>>>Kettle环境已经初始化进行重新初始化<<<<<<<<<<<");
            KettleEnvironment.shutdown();
        }
//        System.getProperties().put("KETTLE_HOME", "/Users/selegant/Downloads/data-integration");
        System.getProperties().put("KETTLE_PLUGIN_BASE_FOLDERS", pluginsPath);
//
//        StepPluginType stepPluginType = StepPluginType.getInstance();
//        PluginRegistry.addPluginType(stepPluginType);
//        for (PluginFolderInterface pluginFolder : stepPluginType.getPluginFolders()) {
//            logger.info("插件地址为"+pluginFolder.getFolder());
//        }

//        PluginRegistry.addPluginType(RowDistributionPluginType.getInstance());
//        PluginRegistry.addPluginType(LogTablePluginType.getInstance());
//        PluginRegistry.addPluginType(CartePluginType.getInstance());
//        PluginRegistry.addPluginType(CompressionPluginType.getInstance());
//        PluginRegistry.addPluginType(AuthenticationProviderPluginType.getInstance());
//        PluginRegistry.addPluginType(AuthenticationConsumerPluginType.getInstance());
//        PluginRegistry.addPluginType(PartitionerPluginType.getInstance());
//        PluginRegistry.addPluginType(JobEntryPluginType.getInstance());
//        PluginRegistry.addPluginType(RepositoryPluginType.getInstance());
//        PluginRegistry.addPluginType(DatabasePluginType.getInstance());
//        PluginRegistry.addPluginType(LifecyclePluginType.getInstance());
//        PluginRegistry.addPluginType(KettleLifecyclePluginType.getInstance());
//        PluginRegistry.addPluginType(ImportRulePluginType.getInstance());

//        PluginRegistry.init();
//
//        KettleVariablesList.init();

        KettleEnvironment.init();

        logger.info(">>>>>>>>>>>系统启动初始化Kettle环境成功<<<<<<<<<<<");
        KettleDatabaseRepository repository = new KettleDatabaseRepository();
        try {
            KettleRepository kettleRepository = kettleRepositoryMapper.getUsedKettleRepository();
            initKettleRepository(repository, kettleRepository);
        } catch (KettleException e) {
            logger.info(">>>>>>>>>>>初始化Kettle环境失败<<<<<<<<<<<");
            logger.error(e.getMessage(),e);
            throw new KettleException("初始化失败");
        }
        return repository;
    }

    public KettleDatabaseRepository loadKettleDatabaseRepository(KettleRepository kettleRepository) throws KettleException {
        logger.info(">>>>>>>>>>>系统启动初始化Kettle环境开始<<<<<<<<<<<");
        if(KettleEnvironment.isInitialized()){
            logger.info(">>>>>>>>>>>Kettle环境已经初始化进行重新初始化<<<<<<<<<<<");
            KettleEnvironment.shutdown();
        }
        System.getProperties().put("KETTLE_PLUGIN_BASE_FOLDERS", pluginsPath);


        KettleEnvironment.init();

        logger.info(">>>>>>>>>>>系统启动初始化Kettle环境成功<<<<<<<<<<<");
        KettleDatabaseRepository repository = new KettleDatabaseRepository();
        try {
            initKettleRepository(repository, kettleRepository);
        } catch (KettleException e) {
            logger.info(">>>>>>>>>>>初始化Kettle环境失败<<<<<<<<<<<");
            logger.error(e.getMessage(),e);
            throw new KettleException("初始化失败");
        }
        return repository;
    }

    private void initKettleRepository(KettleDatabaseRepository repository, KettleRepository kettleRepository) throws KettleException {
        KettleDatabaseRepositoryMeta repositoryMeta = new KettleDatabaseRepositoryMeta();
        DatabaseMeta databaseMeta = new DatabaseMeta(kettleRepository.getRepositoryName(), kettleRepository.getRepositoryType(), kettleRepository.getDatabaseAccess(), kettleRepository.getDatabaseHost(), kettleRepository.getDatabaseName(), kettleRepository.getDatabasePort(), kettleRepository.getDatabaseUsername(), kettleRepository.getDatabasePassword());
        repositoryMeta.setConnection(databaseMeta);
        repository.init(repositoryMeta);
        repository.connect(kettleRepository.getRepositoryUsername(), kettleRepository.getRepositoryPassword());
    }
}
