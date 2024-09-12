package org.example.gc_coffee.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.example.gc_coffee.entity.Order;
import org.example.gc_coffee.entity.QOrder;
import org.example.gc_coffee.entity.QOrderProduct;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public class DslOrderRepositoryImpl implements DslOrderRepository {

    private JPAQueryFactory jpaQueryFactory;

    public DslOrderRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<Order> findAllByEmailWithOrderProducts(String email) {
        QOrder order = QOrder.order;
        QOrderProduct orderProduct = QOrderProduct.orderProduct;

        return jpaQueryFactory
                .selectFrom(order)
                .join(order.orderProducts, orderProduct).fetchJoin() // FETCH JOIN
                .where(order.email.eq(email))
                .fetch();
    }

    @Override
    public List<Order> findAllWithOrderProducts() {
        QOrder order = QOrder.order;
        QOrderProduct orderProduct = QOrderProduct.orderProduct;

        return jpaQueryFactory
                .selectFrom(order)
                .join(order.orderProducts, orderProduct).fetchJoin() // FETCH JOIN
                .fetch();
    }


}
