package com.lxy.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

@Configuration
@EnableAuthorizationServer
public class OAuth2Config extends AuthorizationServerConfigurerAdapter {
  @Autowired
  @Qualifier("authenticationManagerBean")
  private AuthenticationManager authenticationManager;

  // 配置客户端基本信息
  @Override
  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

    //注意：使用springsecurity5，需要加上{noop}指定使用NoOpPasswordEncoder给DelegatingPasswordEncoder去校验密码
    //，同时需要知道NoOpPasswordEncoder已经过时了，这里仅是为了方便
    clients.inMemory().withClient("user-service")
        .secret("{noop}123456")
        .scopes("service")
        .authorizedGrantTypes("refresh_token", "password")
        .accessTokenValiditySeconds(60)
        .refreshTokenValiditySeconds(3600);
  }

  @Override
  public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    endpoints.tokenStore(tokenStore()).tokenEnhancer(jwtAccessTokenConverter())
        .authenticationManager(authenticationManager);
  }

  public TokenStore tokenStore() {
    return new JwtTokenStore(jwtAccessTokenConverter());
  }

  private JwtAccessTokenConverter jwtAccessTokenConverter() {
    KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("oauth2-jwt.jks"),
        "123456".toCharArray());
    JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
    converter.setKeyPair(keyStoreKeyFactory.getKeyPair("oauth2-jwt"));
    return converter;
  }
}
