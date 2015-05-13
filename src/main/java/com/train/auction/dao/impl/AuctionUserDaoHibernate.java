package com.train.auction.dao.impl;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.train.auction.dao.AuctionUserDao;
import com.train.auction.model.AuctionUser;
import com.train.auction.service.impl.ExecuteResult;

public class AuctionUserDaoHibernate extends HibernateDaoSupport implements
		AuctionUserDao {
	/**
	 * ����id�����û�
	 * 
	 * @param id
	 *            ��Ҫ���ҵ��û�id
	 */
	public AuctionUser get(BigInteger id) {
		return (AuctionUser) getHibernateTemplate().get(AuctionUser.class, id);
	}

	/**
	 * �����û�
	 * 
	 * @param user
	 *            ��Ҫ���ӵ��û�
	 */
	public void save(AuctionUser user) {
		getHibernateTemplate().save(user);
	}

	/**
	 * �޸��û�
	 * 
	 * @param user
	 *            ��Ҫ�޸ĵ��û�
	 */
	public void update(AuctionUser user) {
		getHibernateTemplate().saveOrUpdate(user);
	}

	/**
	 * ɾ���û�
	 * 
	 * @param id
	 *            ��Ҫɾ�����û�id
	 */
	public void delete(BigInteger id) {
		getHibernateTemplate().delete(get(id));
	}

	/**
	 * ɾ���û�
	 * 
	 * @param user
	 *            ��Ҫɾ�����û�
	 */
	public void delete(AuctionUser user) {
		getHibernateTemplate().delete(user);
	}

	/**
	 * ��ѯȫ���û�
	 * 
	 * @return ���ȫ���û�
	 */
	public List<AuctionUser> findAll() {
		return (List<AuctionUser>) getHibernateTemplate().find(
				"from AuctionUser");
	}

	/**
	 * �����û�������������û�
	 * 
	 * @param username
	 *            ��ѯ������û���
	 * @param pass
	 *            ��ѯ���������
	 * @return ָ���û����������Ӧ���û�
	 */
	public AuctionUser findUserByNameAndPass(String username, String pass) {
		// ִ��HQL��ѯ
		List<AuctionUser> ul = (List<AuctionUser>) getHibernateTemplate()
				.find("from AuctionUser au where au.username = ? and au.userpass = ?",
						username, pass);
		// ���ز�ѯ�õ��ĵ�һ��AuctionUser����
		if (ul.size() == 1) {
			return (AuctionUser) ul.get(0);
		}
		return null;
	}

	public Query getQuery(AuctionUser criteria) {
		// TODO Auto-generated method stub
		return null;
	}

	public AuctionUser findOne(Query query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void dropCollection() {
		// TODO Auto-generated method stub

	}

	@Override
	public ExecuteResult registUser(String username, String pass, String email) {
		List<AuctionUser> ul = (List<AuctionUser>) getHibernateTemplate().find(
				"from AuctionUser au where au.email = ?", email);
		
		if (ul.size() > 0) {
			return ExecuteResult.createFailedResult("��email�Ѿ�����ע�ᣡ");
		}
		ul = (List<AuctionUser>) getHibernateTemplate().find(
				"from AuctionUser au where au.email = ? and au.username =?",
				email, username);
		
		if (ul.size() > 0) {
			return ExecuteResult.createFailedResult("���û����Ѿ�����ע�ᣡ");
		}

		AuctionUser criteriaUser = new AuctionUser();
		criteriaUser.setEmail(email);
		criteriaUser.setUsername(username);
		criteriaUser.setUserpass(pass);
		try {
			this.save(criteriaUser);
			return ExecuteResult.createSuccdResult("�����û��ɹ�");
		} catch (Exception e) {
			return ExecuteResult.createFailedResult("�����û�ʧ��");
		}
		
	}
}