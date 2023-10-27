package com.example.tdd_start.chap07.autodebit;

public class StubAutoDebitInfoRepository implements AutoDebitInfoRepository {
    @Override
    public void save(AutoDebitInfo info) {

    }

    @Override
    public AutoDebitInfo findOne(String userId) {
        return null;
    }
}
