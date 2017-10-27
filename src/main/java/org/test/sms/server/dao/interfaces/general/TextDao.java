package org.test.sms.server.dao.interfaces.general;

import org.test.sms.common.entity.general.Text;

import java.util.List;

public interface TextDao extends AbstractDao<Text> {

    List<Text> getListForSelection();

    boolean exists(String key);
}