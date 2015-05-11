package com.train.auction.dao.impl.mongo;

import java.math.BigInteger;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.train.auction.dao.KindDao;
import com.train.auction.model.Kind;
import com.train.auction.model.State;

@Repository("kindDao")
public class KindDaoMongo extends AbstractBaseMongoTemplete<Kind> implements KindDao {

	@Override
	protected Class<Kind> getEntityClass() {
		// TODO Auto-generated method stub
		return Kind.class;
	}
	
	private Kind getKind(String name) {
		Query q = getQuery(new Kind(null, name, null));
		Kind kind = findOne(q);
		return kind;
	}

	@Override
	public Kind get(BigInteger id) {
		String name = "拍卖中";

		if (id.doubleValue() <= 3) {
			switch (id.toString()) {
			case "1":
				name = "拍卖中";
				break;
			case "2":
				name = "拍卖成功";
				break;
			case "3":
				name = "流拍";
				break;
			}
			return this.getKind(name);
		}
		return super.get(id);
	}

	
	public Query getQuery(Kind kind) {		
		Query query = new Query();
		if (kind.getId() != null) {
			Criteria criteria = Criteria.where("id").is(kind.getId());
			query.addCriteria(criteria);
		}

		if (kind.getKindName() != null) {
			Criteria criteria = Criteria.where("kindName").regex(
					"^" + kind.getKindName());
			query.addCriteria(criteria);
		}
		return query;
	}
}
