package com.glushkov.shop;

import com.glushkov.shop.web.WebApplicationContext;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class OnlineShopAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{RootApplicationContext.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{WebApplicationContext.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/*"};
    }
}







/*    @Override
    protected Filter[] getServletFilters() {
        return new Filter[]{new AdminRoleSecurityFilter(), new UserRoleSecurityFilter()};
    }*/


//Циклическое перенаправление, секьюрити сервис = null
//web application Value не работает
// на / нету маппинга

    /*
    <filter-mapping>
<filter-name>UserRoleSecurityFilter</filter-name>
<url-pattern>/home</url-pattern>
<url-pattern/>
<url-pattern>/</url-pattern>
<url-pattern>/search</url-pattern>
<url-pattern>/view/*</url-pattern>
<url-pattern>/logout</url-pattern>
<url-pattern>/cart</url-pattern>
</filter-mapping>
<filter-mapping>
<filter-name>AdminRoleSecurityFilter</filter-name>
<url-pattern>/add</url-pattern>
<url-pattern>/edit/*</url-pattern>
<url-pattern>/delete</url-pattern>
</filter-mapping>
     */

/*    @Bean
    protected DataSource dataSource(@Value("${db.url}") String url,
                                    @Value("${db.user}") String userName,
                                    @Value("${db.password}") String password) {

        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(url);
        dataSource.setUser(userName);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean
    protected UserDao userDao(DataSource dataSource) {
        return new JdbcUserDao(dataSource);
    }

    @Bean
    protected UserService userService(UserDao userDao) {
        return new DefaultUserService(userDao);
    }

    @Bean
    protected DefaultSecurityService securityService(UserService userService) {
        return new DefaultSecurityService(userService);
    }

    @Bean
    protected AdminRoleSecurityFilter adminRoleSecurityFilter(DefaultSecurityService securityService) {
        return new AdminRoleSecurityFilter(securityService);
    }

    @Bean
    protected UserRoleSecurityFilter userRoleSecurityFilter(DefaultSecurityService securityService) {
        return new UserRoleSecurityFilter(securityService);
    }

    @Autowired
    private AdminRoleSecurityFilter adminRoleSecurityFilter;
    @Autowired
    private UserRoleSecurityFilter userRoleSecurityFilter;
    @Override
    protected Filter[] getServletFilters() {
        AdminRoleSecurityFilter adminRoleSecurityFilter = new AdminRoleSecurityFilter();
        UserRoleSecurityFilter userRoleSecurityFilter = new UserRoleSecurityFilter();
        log.info("Filters: {}, {}", adminRoleSecurityFilter, userRoleSecurityFilter);
        return new Filter[]{adminRoleSecurityFilter, userRoleSecurityFilter};
}
*/

/*    @Bean
    protected AdminRoleSecurityFilter adminRoleSecurityFilter() {
        return new AdminRoleSecurityFilter();
    }

    @Bean
    protected UserRoleSecurityFilter userRoleSecurityFilter() {
        return new UserRoleSecurityFilter();
    }*/

/*    @Bean
    protected DataSource dataSource(@Value("${db.url}") String url,
                                    @Value("${db.user}") String userName,
                                    @Value("${db.password}") String password) {

        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(url);
        dataSource.setUser(userName);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean
    protected UserDao userDao(DataSource dataSource) {
        return new JdbcUserDao(dataSource);
    }

    @Bean
    protected UserService userService(UserDao userDao) {
        return new DefaultUserService(userDao);
    }

    @Bean
    protected DefaultSecurityService securityService(UserService userService) {
        return new DefaultSecurityService(userService);
    }

    @Autowired
    private DefaultSecurityService defaultSecurityService;*/
