package com.fly.sync.action;

import com.fly.core.text.json.Jsonable;
import com.fly.sync.contract.AbstractRecordAction;
import com.fly.sync.contract.DbFactory;
import com.fly.sync.mysql.model.Record;
import com.fly.sync.setting.River;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InsertAction implements AbstractRecordAction {

    public final static Logger logger = LoggerFactory.getLogger(InsertAction.class);
    private Record record;

    public InsertAction(Record record) {
        this.record = record;
    }

    public static InsertAction create(Record record)
    {
        return new InsertAction(record);
    }

    @Override
    public void execute(DbFactory dbFactory) {


    }

    @Override
    public DocWriteRequest getRequest(DbFactory dbFactory) throws Exception {

        River.Table table = dbFactory.getRiverDatabase().getTable(record.table);

        return new IndexRequest(table.index, table.type, record.getID(table))
                .source(record.toJson(Jsonable.Builder.makeAdapter()), XContentType.JSON);
    }

    public Record getRecord() {
        return record;
    }

    @Override
    public String getGroup() {
        return AbstractRecordAction.class.getName();
    }
}
