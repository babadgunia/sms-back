package org.test.sms.common.service.general;

import org.test.sms.common.entity.general.Text;

import java.util.List;

public interface TextService extends AbstractService<Text> {

    List<Text> getListForSelection();
}