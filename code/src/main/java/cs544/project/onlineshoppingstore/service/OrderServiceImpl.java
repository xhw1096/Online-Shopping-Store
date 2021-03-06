package cs544.project.onlineshoppingstore.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cs544.project.onlineshoppingstore.dao.OrderDao;
import cs544.project.onlineshoppingstore.dao.OrderlineDao;
import cs544.project.onlineshoppingstore.model.Order;
import cs544.project.onlineshoppingstore.model.OrderStatus;
import cs544.project.onlineshoppingstore.model.Orderline;

@Transactional(propagation = Propagation.REQUIRED)
@Component
public class OrderServiceImpl implements OrderService {

	private OrderDao orderDao;
	
	@Autowired
	private OrderlineDao orderlineDao;
	
	@Autowired
	public void setOrderDao(OrderDao orderDao) {
		this.orderDao = orderDao;
	}
	
	@Override
	public void create(Order order) {
		
		Order o = orderDao.save(order);
		for(Orderline ol: order.getOrderlines()){
			ol.setOrder(o);
			orderlineDao.save(ol);
		}
	}

	@Override
	public void update(long id, Order updatedOrder) {
		updatedOrder.setId(id);
		orderDao.save(updatedOrder);
		
	}

	@Override
	public void delete(long orderId) {
		orderDao.delete(orderId);
		
	}

	@Override
	public List<Order> findByOrderDate(Date date) {
		return orderDao.findByOrderDate(date);
	}

	@Override
	public List<Order> findByOrderStatus(OrderStatus orderStatus) {
		return orderDao.findByOrderStatus(orderStatus);
	}

	@Override
	public Order get(long orderId) {
		return orderDao.findOne(orderId);
	}

	@Override
	public List<Order> getAll() {
		return orderDao.findAll();
	}

}
