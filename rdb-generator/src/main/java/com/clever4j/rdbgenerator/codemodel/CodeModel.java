package com.clever4j.rdbgenerator.codemodel;

import com.clever4j.lang.AllNonnullByDefault;

import java.util.List;

@AllNonnullByDefault
public record CodeModel(
    List<RecordModel> recordModels,
    List<TemplateDaoModel> templateDaoModels,
    List<DaoModel> daoModels,
    List<BaseImplementationDaoModel> baseImplementationDaoModels,
    List<ImplementationDaoModel> implementationDaoModels
) {
}