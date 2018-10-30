package com.training360.yellowcode.businesslogic;

import com.training360.yellowcode.database.OrdersDao;
import com.training360.yellowcode.database.ProductDao;
import com.training360.yellowcode.database.ReportsDao;
import com.training360.yellowcode.dbTables.Orders;
import com.training360.yellowcode.dbTables.Reports;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class ReportsService {

    public static final Logger LOGGER = LoggerFactory.getLogger(ReportsService.class);
    private ReportsDao reportsDao;

    public ReportsService(ReportsDao reportsDao) {
        this.reportsDao = reportsDao;
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<Reports> listReportsByDate() {
        return reportsDao.listReportsByDate();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<Reports> listReportsByProductAndDate() {
        return reportsDao.listReportsByProductAndDate();
    }
}
