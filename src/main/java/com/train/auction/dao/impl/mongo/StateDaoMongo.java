package com.train.auction.dao.impl.mongo;

import java.math.BigInteger;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.train.auction.dao.StateDao;
import com.train.auction.model.State;

@Repository("stateDao")
public class StateDaoMongo extends AbstractBaseMongoTemplete<State> implements
		StateDao {

	@Override
	protected Class<State> getEntityClass() {
		// TODO Auto-generated method stub
		return State.class;
	}

	private State getState(String name) {
		Query stq = getQuery(new State(null, name));
		return findOne(stq);
	}

	@Override
	public State get(BigInteger id) {
		String name = "������";

		if (id.doubleValue() <= 3) {
			switch (id.toString()) {
			case "1":
				name = "������";
				break;
			case "2":
				name = "�����ɹ�";
				break;
			case "3":
				name = "����";
				break;
			}
			return this.getState(name);
		}
		return super.get(id);
	}

	public Query getQuery(State state) {
		Query query = new Query();
		if (state.getId() != null) {
			Criteria criteria = Criteria.where("id").is(state.getId());
			query.addCriteria(criteria);
		}

		if (state.getStateName() != null) {
			Criteria criteria = Criteria.where("stateName").regex(
					"^" + state.getStateName());
			query.addCriteria(criteria);
		}
		return query;
	}
}
