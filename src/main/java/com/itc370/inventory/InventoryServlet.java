package com.itc370.inventory;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/addItem", "/listItems"})
public class InventoryServlet extends HttpServlet {

    private SqlSessionFactory sqlSessionFactory;

    @Override
    public void init() throws ServletException {
        sqlSessionFactory = MyBatisUtil.getSqlSessionFactory();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getRequestURI().endsWith("/addItem")) {
            ObjectMapper mapper = new ObjectMapper();
            InventoryItem item = mapper.readValue(request.getReader(), InventoryItem.class);

            try (SqlSession session = sqlSessionFactory.openSession()) {
                InventoryMapper mapperInterface = session.getMapper(InventoryMapper.class);
                mapperInterface.insertItem(item);
                session.commit();
            }

            response.setContentType("application/json");
            response.getWriter().write("{\"status\":\"Item added successfully\"}");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getRequestURI().endsWith("/listItems")) {
            List<InventoryItem> items;

            try (SqlSession session = sqlSessionFactory.openSession()) {
                InventoryMapper mapperInterface = session.getMapper(InventoryMapper.class);
                items = mapperInterface.getAllItems();
            }

            response.setContentType("application/json");
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(response.getWriter(), items);
        }
    }
}