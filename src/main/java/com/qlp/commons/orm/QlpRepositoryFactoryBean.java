package com.qlp.commons.orm;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

import javax.persistence.EntityManager;
import java.io.Serializable;

/**
 * Created by qlp on 14-4-2.
 *
 */
public class QlpRepositoryFactoryBean<R extends JpaRepository<T,I>,T,I extends Serializable> extends JpaRepositoryFactoryBean<R,T,I>{
    protected RepositoryFactorySupport createRepositoryFactory(EntityManager entityManager)
    {
        return new DynamicQueryRepositoryFactory(entityManager);
    }

    private static class DynamicQueryRepositoryFactory<T, I extends Serializable> extends JpaRepositoryFactory
    {
        private EntityManager entityManager;
        public DynamicQueryRepositoryFactory(EntityManager entityManager) {
            super(entityManager);
            this.entityManager = entityManager;
        }

        protected Object getTargetRepository(RepositoryMetadata metadata)
        {
            return new QlpJpaRepositoryImpl(metadata.getDomainType(),this.entityManager);
        }

        protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
            return QlpJpaRepository.class;
        }
    }
}
