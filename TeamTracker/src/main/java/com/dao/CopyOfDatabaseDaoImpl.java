package com.dao;


/**
 * temp file to know the named parameters usage
 *
 */
public class CopyOfDatabaseDaoImpl{
//
//	private NamedParameterJdbcTemplate jdbcTemplate;
//
//	@Autowired
//	public void setDataSource(DataSource dataSource) {
//		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
//	}
//
//	public NamedParameterJdbcTemplate getJdbcTemplate() {
//		return jdbcTemplate;
//	}
//
//	public OrderOutPutDto getOrder(Integer id) {
//		Map<String, Integer> input = new HashMap<>();
//		input.put("id", id);
//
//		OrderOutPutDto order;
////		try {
//			order = getJdbcTemplate()
//					.queryForObject(
//							PropertyReader.getValue("SELECT_ORDER"),
//							input,
//							new BeanPropertyRowMapper<OrderOutPutDto>(
//									OrderOutPutDto.class));
//			List<OrderOutPutModel> orders = getJdbcTemplate().query(
//					PropertyReader.getValue("SELECT_ORDERLIST"),
//					input,
//					new BeanPropertyRowMapper<OrderOutPutModel>(
//							OrderOutPutModel.class));
//			order.setOrders(orders);
////		} catch (DataAccessException e) {
////			throw e 
////		}
//		
//
//		return order;
//	}
//
//	public Integer createOrder(OrderInputDto order) {
//		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(
//				order);
//		KeyHolder keyHolder = new GeneratedKeyHolder();
//		getJdbcTemplate().update(PropertyReader.getValue("INSERT_ORDER"),
//				paramSource, keyHolder, new String[] { "ORDER_ID" });
//		Integer key = (Integer) keyHolder.getKey();
//
//		List<OrderModel> orders = order.getOrders();
//		for (OrderModel orderModel : orders) {
//			orderModel.setOrderId(key);
//		}
//		SqlParameterSource[] params = SqlParameterSourceUtils
//				.createBatch(orders.toArray());
//
//		getJdbcTemplate().batchUpdate(
//				PropertyReader.getValue("INSERT_ORDERLIST"), params);
//
//		return key;
//	}
//
//	public Boolean archiveOrder(Integer id) {
//		Map<String, Object> parameters = new HashMap<String, Object>();
//		parameters.put("id", id);
//		getJdbcTemplate().update(PropertyReader.getValue("UPDATE_ORDERS"),
//				parameters);
//
//		return true;
//	}
//
//	public void updateItem(ItemModel item) {
//		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(
//				item);
//		getJdbcTemplate().update(PropertyReader.getValue("UPDATE_ITEMS"),
//				paramSource);
//
//	}
//
//	public Integer createItem(ItemModel item) {
//		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(
//				item);
//
//		KeyHolder keyHolder = new GeneratedKeyHolder();
//		getJdbcTemplate().update(PropertyReader.getValue("INSERT_ITEMS"),
//				paramSource, keyHolder, new String[] { "ITEM_ID" });
//		Integer key = (Integer) keyHolder.getKey();
//
//		return key;
//	}
//
//	public List<ItemModel> getAllItems(Boolean isActiveItems) {
//		String sql = PropertyReader.getValue("SELECT_ITEMS");
//		if (isActiveItems) {
//			sql += " " + PropertyReader.getValue("SELECT_ITEMS_WHERE");
//		}
//		sql +=" ORDER BY NAME";
//		List<ItemModel> items = getJdbcTemplate().query(sql,
//				new BeanPropertyRowMapper<ItemModel>(ItemModel.class));
//		return items;
//	}
//
//	public void updateCategory(CategoryModel category) {
//		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(
//				category);
//		getJdbcTemplate().update(PropertyReader.getValue("UPDATE_CATEGORY"),
//				paramSource);
//	}
//
//	public void createCategory(CategoryModel category) {
//		Map<String, Object> parameters = new HashMap<String, Object>();
//		parameters.put("name", category.getName());
//		getJdbcTemplate().update(PropertyReader.getValue("INSERT_CATEGORY"),
//				parameters);
//	}
//
//	public List<CategoryModel> getAllCategories(Boolean isActive) {
//		String sql = PropertyReader.getValue("SELECT_CATEGORY");
//		if (isActive) {
//			sql += " WHERE ARCHIVED = 0";
//		}
//		List<CategoryModel> categories = getJdbcTemplate().query(sql,
//				new BeanPropertyRowMapper<CategoryModel>(CategoryModel.class));
//		return categories;
//	}
//	
//	public void updateOrderType(CategoryModel restorant) {
//		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(
//				restorant);
//		getJdbcTemplate().update(PropertyReader.getValue("UPDATE_ORDER_TYPE"),
//				paramSource);
//	}
//
//	public void createOrderType(CategoryModel restorant) {
//		Map<String, Object> parameters = new HashMap<String, Object>();
//		parameters.put("name", restorant.getName());
//		getJdbcTemplate().update(PropertyReader.getValue("INSERT_ORDER_TYPE"),
//				parameters);
//	}
//
//	public List<CategoryModel> getAllOrderTypes(Boolean isActive) {
//		String sql = PropertyReader.getValue("SELECT_ORDER_TYPE");
//		if (isActive) {
//			sql += " WHERE ARCHIVED = 0";
//		}
//		List<CategoryModel> categories = getJdbcTemplate().query(sql,
//				new BeanPropertyRowMapper<CategoryModel>(CategoryModel.class));
//		return categories;
//	}
//
//	public void updateRestorant(CategoryModel restorant) {
//		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(
//				restorant);
//		getJdbcTemplate().update(PropertyReader.getValue("UPDATE_RESTORANT"),
//				paramSource);
//	}
//
//	public void createRestorant(CategoryModel restorant) {
//		Map<String, Object> parameters = new HashMap<String, Object>();
//		parameters.put("name", restorant.getName());
//		getJdbcTemplate().update(PropertyReader.getValue("INSERT_RESTORANT"),
//				parameters);
//	}
//
//	public List<CategoryModel> getAllRestorants(Boolean isActive) {
//		String sql = PropertyReader.getValue("SELECT_RESTORANT");
//		if (isActive) {
//			sql += " WHERE ARCHIVED = 0";
//		}
//		List<CategoryModel> categories = getJdbcTemplate().query(sql,
//				new BeanPropertyRowMapper<CategoryModel>(CategoryModel.class));
//		return categories;
//	}
//
//	public List<Categories> getAllCategoriesCost(boolean isActive, int restId) {
//		List<CategoriesModel> results = getJdbcTemplate().query(
//				PropertyReader.getValue("SELECT_CATEGORIES_WITH_COST"),
//				new BeanPropertyRowMapper<CategoriesModel>(
//						CategoriesModel.class));
//		List<Categories> categories = new ArrayList<>();
//		String catName = null;
//		Categories cat = null;
//
//		for (CategoriesModel result : results) {
//			if (catName == null || !result.getName().equals(catName)) {
//				catName = result.getName();
//				cat = new Categories(catName);
//				categories.add(cat);
//			}
//			Item item = new Item(result);
//			cat.addItems(item);
//
//		}
//
//		return categories;
//	}
//
//	public List<ItemWiseChart> getAllItemWiseTotal(Date fromDate, Date toDate) {
//		Map<String, Object> parameters = new HashMap<String, Object>();
//		parameters.put("fromDate", fromDate);
//		parameters.put("toDate", toDate);
//		List<ItemWiseChart> results = getJdbcTemplate().query(
//				PropertyReader.getValue("ORDER_ITEM_WISE_TOTAL"),parameters,
//				new BeanPropertyRowMapper<ItemWiseChart>(
//						ItemWiseChart.class));
//		return results;
//	}
//
//	public List<CustomerModel> getCustomer(Long phNum, String email) {
//		Map<String, Object> parameters = new HashMap<String, Object>();
//		String query = PropertyReader.getValue("SELECT_CUSTOMERS");
//		boolean isPhNumGiven = phNum != null && phNum > 0;
//		if (isPhNumGiven) {
//			query += " WHERE PHONE_NUM=:phNum"; 
//			parameters.put("phNum", phNum);
//		}
//		if (email != null && email.trim().length() > 0) {
//			if(isPhNumGiven){
//				query += " AND EMAIL_ID=:email";
//			} else {
//				query += " WHERE EMAIL_ID=:email";
//			}
//			parameters.put("email", email.toLowerCase());
//		}
//		List<CustomerModel> customers = getJdbcTemplate().query(
//				query,parameters,
//				new BeanPropertyRowMapper<CustomerModel>(
//						CustomerModel.class));
//		return customers;
//	}
//
//	public Integer createCustomer(CustomerModel customer) {
//		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(
//				customer);
//
//		KeyHolder keyHolder = new GeneratedKeyHolder();
//		getJdbcTemplate().update(PropertyReader.getValue("INSERT_CUSTOMER"),
//				paramSource, keyHolder, new String[] { "CUSTOMER_ID" });
//		Integer key = (Integer) keyHolder.getKey();
//
//		return key;
//	}
//
//	public void updateCustomer(CustomerModel customer) {
//		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(
//				customer);
//		getJdbcTemplate().update(PropertyReader.getValue("UPDATE_CUSTOMER"),
//				paramSource);
//		
//	}
//
//	public List<CategoryModel> getAllPurchaseEntryItem(Boolean isActive) {
//		String sql = PropertyReader.getValue("SELECT_PURCHASE_ENTRY");
//		if (isActive) {
//			sql += " WHERE ARCHIVED = 0";
//		}
//		List<CategoryModel> categories = getJdbcTemplate().query(sql,
//				new BeanPropertyRowMapper<CategoryModel>(CategoryModel.class));
//		return categories;
//	}
//
//	public Boolean addPurchaseEntryItem(String item) {
//		Map<String, String> input = new HashMap<String, String>();
//		input.put("item", item);
//		getJdbcTemplate().update(PropertyReader.getValue("INSERT_PURCHASE_ENTRY"),
//				input);
//		return true;
//	}
//
//	public Boolean updatePurchaseEntry(CategoryModel item) {
//		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(
//				item);
//		getJdbcTemplate().update(PropertyReader.getValue("UPDATE_PURCHASE_ENTRY"),
//				paramSource);
//		return true;
//	}
//
}
