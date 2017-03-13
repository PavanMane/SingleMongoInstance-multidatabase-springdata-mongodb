package org.p1.config;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.p1.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.BulkOperations.BulkMode;
import org.springframework.data.mongodb.core.CollectionCallback;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.DbCallback;
import org.springframework.data.mongodb.core.DocumentCallbackHandler;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.IndexOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.ScriptOperations;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.core.mapreduce.GroupBy;
import org.springframework.data.mongodb.core.mapreduce.GroupByResults;
import org.springframework.data.mongodb.core.mapreduce.MapReduceOptions;
import org.springframework.data.mongodb.core.mapreduce.MapReduceResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.util.CloseableIterator;

import com.mongodb.CommandResult;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.ReadPreference;
import com.mongodb.WriteResult;

public class MongoTenantTemplate extends MongoTemplate {
	private static Map<String, MongoTemplate> tenantTemplates = new HashMap<String, MongoTemplate>();
	
	@Autowired
	public MongoDBCredentials mongoDBCredentials;
	
	public MongoTenantTemplate(MongoDbFactory mongoDbFactory) {
		super(mongoDbFactory);
		tenantTemplates.put(mongoDbFactory.getDb().getName(), new MongoTemplate(mongoDbFactory));
	}

	protected MongoTemplate getTenantMongoTemplate(String tenant) {
		MongoTemplate mongoTemplate = tenantTemplates.get(tenant);
		if (mongoTemplate == null) {
			SimpleMongoDbFactory mongoDbFactory = new SimpleMongoDbFactory(
					new MongoClient(new MongoClientURI(mongoDBCredentials.getUri())), tenant);
			mongoTemplate = new MongoTemplate(mongoDbFactory);
			tenantTemplates.put(tenant, mongoTemplate);
		}
		return mongoTemplate;
	}

	@Override
	public String getCollectionName(Class<?> entityClass) {
		return getTenantMongoTemplate(TenantContext.getTenant()).getCollectionName(entityClass);
	}

	@Override
	public CommandResult executeCommand(String jsonCommand) {
		return getTenantMongoTemplate(TenantContext.getTenant()).executeCommand(jsonCommand);
	}

	@Override
	public CommandResult executeCommand(DBObject command) {
		return getTenantMongoTemplate(TenantContext.getTenant()).executeCommand(command);
	}

	@Override
	@SuppressWarnings("deprecation")
	public CommandResult executeCommand(DBObject command, int options) {
		return getTenantMongoTemplate(TenantContext.getTenant()).executeCommand(command, options);
	}

	@Override
	public CommandResult executeCommand(DBObject command, ReadPreference readPreference) {
		return getTenantMongoTemplate(TenantContext.getTenant()).executeCommand(command, readPreference);
	}

	@Override
	public void executeQuery(Query query, String collectionName, DocumentCallbackHandler dch) {
		getTenantMongoTemplate(TenantContext.getTenant()).executeQuery(query, collectionName, dch);

	}

	@Override
	public <T> T execute(DbCallback<T> action) {
		return getTenantMongoTemplate(TenantContext.getTenant()).execute(action);
	}

	@Override
	public <T> T execute(Class<?> entityClass, CollectionCallback<T> action) {
		return getTenantMongoTemplate(TenantContext.getTenant()).execute(entityClass, action);
	}

	@Override
	public <T> T execute(String collectionName, CollectionCallback<T> action) {
		return getTenantMongoTemplate(TenantContext.getTenant()).execute(collectionName, action);
	}

	@SuppressWarnings("deprecation")
	@Override
	public <T> T executeInSession(DbCallback<T> action) {
		return getTenantMongoTemplate(TenantContext.getTenant()).executeInSession(action);
	}

	@Override
	public <T> CloseableIterator<T> stream(Query query, Class<T> entityType) {
		return getTenantMongoTemplate(TenantContext.getTenant()).stream(query, entityType);
	}

	@Override
	public <T> DBCollection createCollection(Class<T> entityClass) {
		return getTenantMongoTemplate(TenantContext.getTenant()).createCollection(entityClass);
	}

	@Override
	public <T> DBCollection createCollection(Class<T> entityClass, CollectionOptions collectionOptions) {
		return getTenantMongoTemplate(TenantContext.getTenant()).createCollection(entityClass, collectionOptions);
	}

	@Override
	public DBCollection createCollection(String collectionName) {
		return getTenantMongoTemplate(TenantContext.getTenant()).createCollection(collectionName);
	}

	@Override
	public DBCollection createCollection(String collectionName, CollectionOptions collectionOptions) {
		return getTenantMongoTemplate(TenantContext.getTenant()).createCollection(collectionName, collectionOptions);
	}

	@Override
	public Set<String> getCollectionNames() {
		return getTenantMongoTemplate(TenantContext.getTenant()).getCollectionNames();
	}

	@Override
	public DBCollection getCollection(String collectionName) {
		return getTenantMongoTemplate(TenantContext.getTenant()).getCollection(collectionName);
	}

	@Override
	public <T> boolean collectionExists(Class<T> entityClass) {
		return getTenantMongoTemplate(TenantContext.getTenant()).collectionExists(entityClass);
	}

	@Override
	public boolean collectionExists(String collectionName) {
		return getTenantMongoTemplate(TenantContext.getTenant()).collectionExists(collectionName);
	}

	@Override
	public <T> void dropCollection(Class<T> entityClass) {
		getTenantMongoTemplate(TenantContext.getTenant()).dropCollection(entityClass);
	}

	@Override
	public void dropCollection(String collectionName) {
		getTenantMongoTemplate(TenantContext.getTenant()).dropCollection(collectionName);

	}

	@Override
	public IndexOperations indexOps(String collectionName) {
		return getTenantMongoTemplate(TenantContext.getTenant()).indexOps(collectionName);
	}

	@Override
	public IndexOperations indexOps(Class<?> entityClass) {
		return getTenantMongoTemplate(TenantContext.getTenant()).indexOps(entityClass);
	}

	@Override
	public ScriptOperations scriptOps() {
		return getTenantMongoTemplate(TenantContext.getTenant()).scriptOps();
	}

	@Override
	public BulkOperations bulkOps(BulkMode mode, String collectionName) {
		return getTenantMongoTemplate(TenantContext.getTenant()).bulkOps(mode, collectionName);
	}

	@Override
	public BulkOperations bulkOps(BulkMode mode, Class<?> entityType) {
		return getTenantMongoTemplate(TenantContext.getTenant()).bulkOps(mode, entityType);
	}

	@Override
	public BulkOperations bulkOps(BulkMode mode, Class<?> entityType, String collectionName) {
		return getTenantMongoTemplate(TenantContext.getTenant()).bulkOps(mode, entityType, collectionName);
	}

	@Override
	public <T> List<T> findAll(Class<T> entityClass) {
		return getTenantMongoTemplate(TenantContext.getTenant()).findAll(entityClass);
	}

	@Override
	public <T> List<T> findAll(Class<T> entityClass, String collectionName) {
		return getTenantMongoTemplate(TenantContext.getTenant()).findAll(entityClass, collectionName);
	}

	@Override
	public <T> GroupByResults<T> group(String inputCollectionName, GroupBy groupBy, Class<T> entityClass) {
		return getTenantMongoTemplate(TenantContext.getTenant()).group(inputCollectionName, groupBy, entityClass);
	}

	@Override
	public <T> GroupByResults<T> group(Criteria criteria, String inputCollectionName, GroupBy groupBy,
			Class<T> entityClass) {
		return getTenantMongoTemplate(TenantContext.getTenant()).group(criteria, inputCollectionName, groupBy,
				entityClass);
	}

	@Override
	public <O> AggregationResults<O> aggregate(TypedAggregation<?> aggregation, String collectionName,
			Class<O> outputType) {
		return getTenantMongoTemplate(TenantContext.getTenant()).aggregate(aggregation, outputType);
	}

	@Override
	public <O> AggregationResults<O> aggregate(TypedAggregation<?> aggregation, Class<O> outputType) {
		return getTenantMongoTemplate(TenantContext.getTenant()).aggregate(aggregation, outputType);
	}

	@Override
	public <O> AggregationResults<O> aggregate(Aggregation aggregation, Class<?> inputType, Class<O> outputType) {
		return getTenantMongoTemplate(TenantContext.getTenant()).aggregate(aggregation, inputType, outputType);
	}

	@Override
	public <O> AggregationResults<O> aggregate(Aggregation aggregation, String collectionName, Class<O> outputType) {
		return getTenantMongoTemplate(TenantContext.getTenant()).aggregate(aggregation, collectionName, outputType);
	}

	@Override
	public <T> MapReduceResults<T> mapReduce(String inputCollectionName, String mapFunction, String reduceFunction,
			Class<T> entityClass) {
		return getTenantMongoTemplate(TenantContext.getTenant()).mapReduce(inputCollectionName, mapFunction,
				reduceFunction, entityClass);
	}

	@Override
	public <T> MapReduceResults<T> mapReduce(String inputCollectionName, String mapFunction, String reduceFunction,
			MapReduceOptions mapReduceOptions, Class<T> entityClass) {
		return getTenantMongoTemplate(TenantContext.getTenant()).mapReduce(inputCollectionName, mapFunction,
				reduceFunction, mapReduceOptions, entityClass);
	}

	@Override
	public <T> MapReduceResults<T> mapReduce(Query query, String inputCollectionName, String mapFunction,
			String reduceFunction, Class<T> entityClass) {
		return getTenantMongoTemplate(TenantContext.getTenant()).mapReduce(query, inputCollectionName, mapFunction,
				reduceFunction, entityClass);
	}

	@Override
	public <T> MapReduceResults<T> mapReduce(Query query, String inputCollectionName, String mapFunction,
			String reduceFunction, MapReduceOptions mapReduceOptions, Class<T> entityClass) {
		return mapReduce(query, inputCollectionName, mapFunction, reduceFunction, mapReduceOptions, entityClass);
	}

	@Override
	public <T> GeoResults<T> geoNear(NearQuery near, Class<T> entityClass) {
		return getTenantMongoTemplate(TenantContext.getTenant()).geoNear(near, entityClass);
	}

	@Override
	public <T> GeoResults<T> geoNear(NearQuery near, Class<T> entityClass, String collectionName) {
		return getTenantMongoTemplate(TenantContext.getTenant()).geoNear(near, entityClass, collectionName);
	}

	@Override
	public <T> T findOne(Query query, Class<T> entityClass) {
		return getTenantMongoTemplate(TenantContext.getTenant()).findOne(query, entityClass);
	}

	@Override
	public <T> T findOne(Query query, Class<T> entityClass, String collectionName) {
		return getTenantMongoTemplate(TenantContext.getTenant()).findOne(query, entityClass, collectionName);
	}

	@Override
	public boolean exists(Query query, String collectionName) {
		return getTenantMongoTemplate(TenantContext.getTenant()).exists(query, collectionName);
	}

	@Override
	public boolean exists(Query query, Class<?> entityClass) {
		return getTenantMongoTemplate(TenantContext.getTenant()).exists(query, entityClass);
	}

	@Override
	public boolean exists(Query query, Class<?> entityClass, String collectionName) {
		return getTenantMongoTemplate(TenantContext.getTenant()).exists(query, entityClass, collectionName);
	}

	@Override
	public <T> List<T> find(Query query, Class<T> entityClass) {
		return getTenantMongoTemplate(TenantContext.getTenant()).find(query, entityClass);
	}

	@Override
	public <T> List<T> find(Query query, Class<T> entityClass, String collectionName) {
		return getTenantMongoTemplate(TenantContext.getTenant()).find(query, entityClass, collectionName);
	}

	@Override
	public <T> T findById(Object id, Class<T> entityClass) {
		return getTenantMongoTemplate(TenantContext.getTenant()).findById(id, entityClass);
	}

	@Override
	public <T> T findById(Object id, Class<T> entityClass, String collectionName) {
		return getTenantMongoTemplate(TenantContext.getTenant()).findById(id, entityClass, collectionName);
	}

	@Override
	public <T> T findAndModify(Query query, Update update, Class<T> entityClass) {
		return getTenantMongoTemplate(TenantContext.getTenant()).findAndModify(query, update, entityClass);
	}

	@Override
	public <T> T findAndModify(Query query, Update update, Class<T> entityClass, String collectionName) {
		return getTenantMongoTemplate(TenantContext.getTenant()).findAndModify(query, update, entityClass,
				collectionName);
	}

	@Override
	public <T> T findAndModify(Query query, Update update, FindAndModifyOptions options, Class<T> entityClass) {
		return getTenantMongoTemplate(TenantContext.getTenant()).findAndModify(query, update, options, entityClass);
	}

	@Override
	public <T> T findAndModify(Query query, Update update, FindAndModifyOptions options, Class<T> entityClass,
			String collectionName) {
		return getTenantMongoTemplate(TenantContext.getTenant()).findAndModify(query, update, options, entityClass,
				collectionName);
	}

	@Override
	public <T> T findAndRemove(Query query, Class<T> entityClass) {
		return getTenantMongoTemplate(TenantContext.getTenant()).findAndRemove(query, entityClass);
	}

	@Override
	public <T> T findAndRemove(Query query, Class<T> entityClass, String collectionName) {
		return getTenantMongoTemplate(TenantContext.getTenant()).findAndRemove(query, entityClass, collectionName);
	}

	@Override
	public long count(Query query, Class<?> entityClass) {
		return getTenantMongoTemplate(TenantContext.getTenant()).count(query, entityClass);
	}

	@Override
	public long count(Query query, String collectionName) {
		return getTenantMongoTemplate(TenantContext.getTenant()).count(query, collectionName);
	}

	@Override
	public long count(Query query, Class<?> entityClass, String collectionName) {
		return getTenantMongoTemplate(TenantContext.getTenant()).count(query, entityClass, collectionName);
	}

	@Override
	public void insert(Object objectToSave) {
		getTenantMongoTemplate(TenantContext.getTenant()).insert(objectToSave);
	}

	@Override
	public void insert(Object objectToSave, String collectionName) {
		getTenantMongoTemplate(TenantContext.getTenant()).insert(objectToSave, collectionName);
	}

	@Override
	public void insert(Collection<? extends Object> batchToSave, Class<?> entityClass) {
		getTenantMongoTemplate(TenantContext.getTenant()).insert(batchToSave, entityClass);
	}

	@Override
	public void insert(Collection<? extends Object> batchToSave, String collectionName) {
		getTenantMongoTemplate(TenantContext.getTenant()).insert(batchToSave, collectionName);
	}

	@Override
	public void insertAll(Collection<? extends Object> objectsToSave) {
		getTenantMongoTemplate(TenantContext.getTenant()).insertAll(objectsToSave);
	}

	@Override
	public void save(Object objectToSave) {
		getTenantMongoTemplate(TenantContext.getTenant()).save(objectToSave);
	}

	@Override
	public void save(Object objectToSave, String collectionName) {
		getTenantMongoTemplate(TenantContext.getTenant()).save(objectToSave, collectionName);
	}

	@Override
	public WriteResult upsert(Query query, Update update, Class<?> entityClass) {
		return getTenantMongoTemplate(TenantContext.getTenant()).upsert(query, update, entityClass);
	}

	@Override
	public WriteResult upsert(Query query, Update update, String collectionName) {
		return getTenantMongoTemplate(TenantContext.getTenant()).upsert(query, update, collectionName);
	}

	@Override
	public WriteResult upsert(Query query, Update update, Class<?> entityClass, String collectionName) {
		return getTenantMongoTemplate(TenantContext.getTenant()).upsert(query, update, entityClass, collectionName);
	}

	@Override
	public WriteResult updateFirst(Query query, Update update, Class<?> entityClass) {
		return getTenantMongoTemplate(TenantContext.getTenant()).updateFirst(query, update, entityClass);
	}

	@Override
	public WriteResult updateFirst(Query query, Update update, String collectionName) {
		return getTenantMongoTemplate(TenantContext.getTenant()).updateFirst(query, update, collectionName);
	}

	@Override
	public WriteResult updateFirst(Query query, Update update, Class<?> entityClass, String collectionName) {
		return getTenantMongoTemplate(TenantContext.getTenant()).updateFirst(query, update, entityClass,
				collectionName);
	}

	@Override
	public WriteResult updateMulti(Query query, Update update, Class<?> entityClass) {
		return getTenantMongoTemplate(TenantContext.getTenant()).updateMulti(query, update, entityClass);
	}

	@Override
	public WriteResult updateMulti(Query query, Update update, String collectionName) {
		return getTenantMongoTemplate(TenantContext.getTenant()).updateMulti(query, update, collectionName);
	}

	@Override
	public WriteResult updateMulti(Query query, Update update, Class<?> entityClass, String collectionName) {
		return getTenantMongoTemplate(TenantContext.getTenant()).updateMulti(query, update, entityClass,
				collectionName);
	}

	@Override
	public WriteResult remove(Object object) {
		return getTenantMongoTemplate(TenantContext.getTenant()).remove(object);
	}

	@Override
	public WriteResult remove(Object object, String collection) {
		return getTenantMongoTemplate(TenantContext.getTenant()).remove(object, collection);
	}

	@Override
	public WriteResult remove(Query query, Class<?> entityClass) {
		return getTenantMongoTemplate(TenantContext.getTenant()).remove(query, entityClass);
	}

	@Override
	public WriteResult remove(Query query, Class<?> entityClass, String collectionName) {
		return getTenantMongoTemplate(TenantContext.getTenant()).remove(query, entityClass, collectionName);
	}

	@Override
	public WriteResult remove(Query query, String collectionName) {
		return getTenantMongoTemplate(TenantContext.getTenant()).remove(query, collectionName);
	}

	@Override
	public <T> List<T> findAllAndRemove(Query query, String collectionName) {
		return getTenantMongoTemplate(TenantContext.getTenant()).findAllAndRemove(query, collectionName);
	}

	@Override
	public <T> List<T> findAllAndRemove(Query query, Class<T> entityClass) {
		return getTenantMongoTemplate(TenantContext.getTenant()).findAllAndRemove(query, entityClass);
	}

	@Override
	public <T> List<T> findAllAndRemove(Query query, Class<T> entityClass, String collectionName) {
		return getTenantMongoTemplate(TenantContext.getTenant()).findAllAndRemove(query, entityClass, collectionName);
	}

	@Override
	public MongoConverter getConverter() {
		return getTenantMongoTemplate(TenantContext.getTenant()).getConverter();
	}
	
	@Override
	public DB getDb() {
		return getTenantMongoTemplate(TenantContext.getTenant()).getDb();
	}

}
