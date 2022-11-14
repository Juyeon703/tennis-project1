package tennis.project.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import tennis.project.domain.Member;
import tennis.project.dto.GoogleOAuthRequest;
import tennis.project.dto.GoogleOAuthResponse;
import tennis.project.service.MemberService;
import org.springframework.beans.factory.annotation.Value;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import tennis.project.web.SessionConst;

@Controller
@RequestMapping("/oauth")
@RequiredArgsConstructor
public class GoogleController {

  final static String GOOGLE_AUTH_BASE_URL = "https://accounts.google.com/o/oauth2/v2/auth";
  final static String GOOGLE_TOKEN_BASE_URL = "https://oauth2.googleapis.com/token";
  final static String GOOGLE_REVOKE_TOKEN_BASE_URL = "https://oauth2.googleapis.com/revoke";

  private final MemberService memberService;

  @Value("${api.client_id}")
  String clientId;
  @Value("${api.client_secret}")
  String clientSecret;
  /**
   * Authentication Code를 전달 받는 엔드포인트
   **/
  @GetMapping("/google")
  public String googleAuth(Model model, @RequestParam(value = "code") String authCode, HttpServletRequest request)
    throws JsonProcessingException {

    //HTTP Request를 위한 RestTemplate
    RestTemplate restTemplate = new RestTemplate();

    //Google OAuth Access Token 요청을 위한 파라미터 세팅
    GoogleOAuthRequest googleOAuthRequestParam = GoogleOAuthRequest
      .builder()
      .clientId(clientId)
      .clientSecret(clientSecret)
      .code(authCode)
      .redirectUri("http://localhost:8080/oauth/google")
      .grantType("authorization_code").build();

    System.out.println("googleOAuthRequestParam = " + googleOAuthRequestParam);

    //JSON 파싱을 위한 기본값 세팅
    //요청시 파라미터는 스네이크 케이스로 세팅되므로 Object mapper에 미리 설정해준다.
    ObjectMapper mapper = new ObjectMapper();
    mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
    mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    System.out.println("mapper = " + mapper);
    
    //AccessToken 발급 요청
    ResponseEntity<String> resultEntity = restTemplate.postForEntity(GOOGLE_TOKEN_BASE_URL, googleOAuthRequestParam, String.class);
    System.out.println("resultEntity = " + resultEntity);
    //Token Request
    GoogleOAuthResponse result = mapper.readValue(resultEntity.getBody(), new TypeReference<GoogleOAuthResponse>() {
    });
    System.out.println("result = " + result);
    //ID Token만 추출 (사용자의 정보는 jwt로 인코딩 되어있다)
    String jwtToken = result.getIdToken();
    System.out.println("jwtToken = " + jwtToken);
    String requestUrl = UriComponentsBuilder.fromHttpUrl("https://oauth2.googleapis.com/tokeninfo")
      .queryParam("id_token", jwtToken).encode().toUriString();
    System.out.println("requestUrl = " + requestUrl);
    String resultJson = restTemplate.getForObject(requestUrl, String.class);
    System.out.println("resultJson = " + resultJson);
    
    Map<String,String> userInfo = mapper.readValue(resultJson, new TypeReference<Map<String, String>>(){});
    String access_token = result.getAccessToken();
    model.addAllAttributes(userInfo);
    model.addAttribute("token", access_token);
    System.out.println("userInfo = " + userInfo);
    System.out.println("email = " + userInfo.get("email"));
    System.out.println("name = " + userInfo.get("name"));
    System.out.println("result.getAccessToken() = " + access_token);

    if (memberService.findByEmail(userInfo.get("email")).isEmpty()) {
      Member member = memberService.googleSignUp(userInfo, access_token);
      request.getSession().setAttribute(SessionConst.LOGIN_MEMBER, member);
    } else {
      Member member = memberService.findByEmail(userInfo.get("email")).get();
      memberService.renewAccessToken(member, access_token);
      request.getSession().setAttribute(SessionConst.LOGIN_MEMBER, member);
    }

    return "redirect:/";

  }

//  /**
//   * 토큰 무효화
//   **/
//  @GetMapping("google/revoke/token")
//  @ResponseBody
//  public Map<String, String> revokeToken(HttpSession session) throws JsonProcessingException {
//
//    Map<String, String> result = new HashMap<>();
//    RestTemplate restTemplate = new RestTemplate();
////    String token = session.getAttribute(SessionConst.LOGIN_MEMBER).getAcc
//    final String requestUrl = UriComponentsBuilder.fromHttpUrl(GOOGLE_REVOKE_TOKEN_BASE_URL)
//      .queryParam("token", token).encode().toUriString();
//
//    System.out.println("TOKEN ? " + token);
//
//    String resultJson = restTemplate.postForObject(requestUrl, null, String.class);
//    result.put("result", "success");
//    result.put("resultJson", resultJson);
//
//    return result;
//
//  }

//  @GetMapping("/google")
//  public String getCI(@RequestParam String code) {
//
//    System.out.println("code = " + code);
//    GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
//      // Specify the CLIENT_ID of the app that accesses the backend:
//      .setAudience(Collections.singletonList("252269695685-2ilmvuqp7rkur8ht1an8lkdt90ep3r6k.apps.googleusercontent.com"))
//      // Or, if multiple clients access the backend:
//      //.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
//      .build();
//
//// (Receive idTokenString by HTTPS POST)
//
//    GoogleIdToken idToken = verifier.verify(idTokenString);
//    if (idToken != null) {
//      Payload payload = idToken.getPayload();
//
//      // Print user identifier
//      String userId = payload.getSubject();
//      System.out.println("User ID: " + userId);
//
//      // Get profile information from payload
//      String email = payload.getEmail();
//      boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
//      String name = (String) payload.get("name");
//      String pictureUrl = (String) payload.get("picture");
//      String locale = (String) payload.get("locale");
//      String familyName = (String) payload.get("family_name");
//      String givenName = (String) payload.get("given_name");
//
//      // Use or store profile information
//      // ...
//
//    } else {
//      System.out.println("Invalid ID token.");
//    }
//    return "redirect:/";
//  }

  @GetMapping("/google/logout")
  public String logout(HttpServletRequest request, Model model) {
//    model.addAttribute("loginForm", new LoginForm()); //카카오 로그인 용
    HttpSession session = request.getSession(false);
    if (session != null) {
      session.invalidate();
    }
    return "redirect:/";
  }
}
