package vn.iotstar.dao.impl;

import jakarta.persistence.*;
import vn.iotstar.configs.JPAConfig;
import vn.iotstar.dao.ICategoryDao;
import vn.iotstar.entity.Category;
import java.util.*;

public class CategoryDao implements ICategoryDao {
	@Override

	public void insert(Category category) {

		EntityManager enma = JPAConfig.getEntityManager();

		EntityTransaction trans = enma.getTransaction();

		try {

			trans.begin();
			enma.persist(category);

			trans.commit();

		} catch (Exception e) {

			e.printStackTrace();

			trans.rollback();

			throw e;

		} finally {

			enma.close();

		}

	}

	@Override

	public void update(Category category) {

		EntityManager enma = JPAConfig.getEntityManager();

		EntityTransaction trans = enma.getTransaction();

		try {

			trans.begin();
			enma.merge(category);

			trans.commit();

		} catch (Exception e) {

			e.printStackTrace();

			trans.rollback();

			throw e;

		} finally {

			enma.close();

		}

	}

	@Override

	public void delete(int cateid) throws Exception {

		EntityManager enma = JPAConfig.getEntityManager();

		EntityTransaction trans = enma.getTransaction();

		try {

			trans.begin();

			Category category = enma.find(Category.class, cateid);

			if (category != null) {

				enma.remove(category);

			} else {

				throw new Exception("Không tìm thấy");

			}

			trans.commit();

		} catch (Exception e) {

			e.printStackTrace();

			trans.rollback();

			throw e;

		} finally {

			enma.close();

		}

	}

	@Override

	public Category findById(int cateid) {

		EntityManager enma = JPAConfig.getEntityManager();

		Category category = enma.find(Category.class, cateid);

		return category;

	}

	@Override

	public List<Category> findAll() {

		EntityManager enma = JPAConfig.getEntityManager();

		TypedQuery<Category> query = enma.createNamedQuery("Category.findAll", Category.class);

		return query.getResultList();

	}

	@Override

	public List<Category> findByCategoryname(String catname) {

		EntityManager enma = JPAConfig.getEntityManager();

		String jpql = "SELECT c FROM Category c WHERE c.catename like :catname";

		TypedQuery<Category> query = enma.createQuery(jpql, Category.class);

		query.setParameter("catename", "%" + catname + "%");

		return query.getResultList();

	}

	@Override

	public List<Category> findAll(int page, int pagesize) {

		EntityManager enma = JPAConfig.getEntityManager();

		TypedQuery<Category> query = enma.createNamedQuery("Category.findAll", Category.class);

		query.setFirstResult(page * pagesize);

		query.setMaxResults(pagesize);

		return query.getResultList();

	}

	@Override

	public int count() {

		EntityManager enma = JPAConfig.getEntityManager();

		String jpql = "SELECT count(c) FROM Category c";

		Query query = enma.createQuery(jpql);

		return ((Long) query.getSingleResult()).intValue();

	}

}
