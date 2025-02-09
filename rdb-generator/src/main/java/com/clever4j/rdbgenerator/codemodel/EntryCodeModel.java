package com.clever4j.rdbgenerator.codemodel;

import com.clever4j.lang.AllNonnullByDefault;

@AllNonnullByDefault
public record EntryCodeModel(
    RecordModel record,
    WhereModel whereModel,
    DaoModel daoModel
) {
}
