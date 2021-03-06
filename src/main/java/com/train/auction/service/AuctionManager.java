package com.train.auction.service;

import java.math.BigInteger;
import java.util.List;

import com.train.auction.business.BidBean;
import com.train.auction.business.ItemBean;
import com.train.auction.business.KindBean;
import com.train.auction.exception.AuctionException;
import com.train.auction.service.impl.ExecuteResult;


public interface AuctionManager  
{
	/**
	 * 根据赢取者查询物品
	 * @param winerId 赢取者的ID
	 * @return 赢取者获得的全部物品
	 */
	List<ItemBean> getItemByWiner(BigInteger winerId) 
		throws AuctionException;

	/**
	 * 查询流拍的全部物品
	 * @return 全部流拍物品
	 */
	List<ItemBean> getFailItems()throws AuctionException;

	/**
	 * 根据用户名，密码验证登录是否成功
	 * @param username 登录的用户名
 	 * @param pass 登录的密码
	 * @return 登录成功返回用户ID，否则返回-1
	 */
	BigInteger validLogin(String username , String pass)
		throws AuctionException;

	
	ExecuteResult registUser(String username , String pass,String email)
			throws AuctionException;
	/**
	 * 查询用户的全部出价
	 * @param userId 竞价用户的ID
	 * @return 用户的全部出价
	 */
	List<BidBean> getBidByUser(BigInteger userId)
		throws AuctionException;

	/**
	 * 根据用户查找目前仍在拍卖中的全部物品
	 * @param userId 所属者的ID
	 * @return 属于当前用户的、处于拍卖中的全部物品。
	 */
	List<ItemBean> getItemsByOwner(BigInteger userId)
		throws AuctionException;

	/**
	 * 查询全部种类
	 * @return 系统中全部全部种类
	 */
	List<KindBean> getAllKind() throws AuctionException; 

	/**
	* 添加物品
	* @param name 物品名称
	* @param desc 物品描述
	* @param remark 物品备注
	* @param avail 有效天数
	* @param kind 物品种类
	* @param userId 添加者的ID
	* @return 新增物品的主键
	*/ 
	BigInteger addItem(String name , String desc , String remark , 
		double initPrice , int avail , BigInteger kind , BigInteger userId) 
		throws AuctionException;

	/**
	 * 添加种类
	 * @param name 种类名称
	 * @param desc 种类描述
	 * @return 新增种类的主键
	 */ 
	BigInteger addKind(String name , String desc) throws AuctionException;

	/**
	 * 根据产品分类，获取处于拍卖中的全部物品
	 * @param kindId 种类id;
	 * @return 该类的全部产品
	 */
	List<ItemBean> getItemsByKind(BigInteger kindId) throws AuctionException;

	/**
	 * 根据种类id获取种类名
	 * @param kindId 种类id;
	 * @return 该种类的名称
	 */
	String getKind(BigInteger kindId) throws AuctionException;

	/**
	 * 根据物品id，获取物品
	 * @param itemId 物品id;
	 * @return 指定id对应的物品
	 */
	ItemBean getItem(BigInteger itemId) throws AuctionException;

	/**
	 * 增加新的竞价，并对竞价用户发邮件通知
	 * @param itemId 物品id;
	 * @param bidPrice 竞价价格
	 * @param userId 竞价用户的ID
	 * @return 返回新增竞价记录的ID
	 */
	BigInteger addBid(BigInteger itemId , double bidPrice ,BigInteger userId)
		throws AuctionException;

	/**
	 * 根据时间来修改物品的赢取者
	 */
	void updateWiner()throws AuctionException;
	
	void initData();
}