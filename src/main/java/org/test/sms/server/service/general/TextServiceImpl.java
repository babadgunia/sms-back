package org.test.sms.server.service.general;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.test.sms.common.entity.general.Text;
import org.test.sms.common.enums.general.ErrorCodeType;
import org.test.sms.common.exception.AppException;
import org.test.sms.common.service.general.TextService;
import org.test.sms.server.dao.interfaces.general.TextDao;

import java.util.List;

@Service
@Transactional
public class TextServiceImpl extends AbstractServiceImpl<Text> implements TextService {

    @Autowired
    public TextServiceImpl(TextDao dao) {
        super(dao);
    }

    @Override
    protected void processEntity(Text entity) {
        entity.getValues().forEach(value -> value.setText(entity));
    }

    @Override
    protected void validateSave(Text entity) throws AppException {
        String key = entity.getKey();
        if (((TextDao) dao).exists(key)) {
            throw new AppException(ErrorCodeType.TEXT_EXISTS, key);
        }
    }

    @Override
    public List<Text> getListForSelection() {
        return ((TextDao) dao).getListForSelection();
    }
}