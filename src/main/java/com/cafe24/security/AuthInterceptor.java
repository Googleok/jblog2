package com.cafe24.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.cafe24.jblog.vo.UserVo;

public class AuthInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 1. handler 종류 확인
		// 종류의 두가지  HandlerMethod, DefaultServletHandler
		// handlermethod가 아닌 assets 나 image 파일들이 interceptor에 걸리지 않게 설정해주는 것
		if(handler instanceof HandlerMethod == false) {
			System.out.println(handler);
			return true;
		}

		// 2. casting 
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		
		// 3. Method의 @Auth 받아오기
		Auth auth = handlerMethod.getMethodAnnotation(Auth.class);
		
		// 4. HandlerMethod에 @Auth 없으면
		//    Class(Type)에 @Auth를 받아오기
//		if(auth == null) {
//			auth = ....
//		}
	
		// 5. @Auth가 안 붙어 있는 경우
		if(auth == null) {
			return true;
		}
		
		// 6. @Auth가 (class 또는 method)에 붙어 있기 때문에
		// 	   인증 여부 체크
		HttpSession session = request.getSession();
		if(session == null) { //인증이 안되어 있음
			response.sendRedirect(request.getContextPath() +"/user/login");
			return false;
		}
		
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			response.sendRedirect(request.getContextPath() +"/user/login");
			return false;
		}

		// 7. Role 가져오기
		Auth.Role role = auth.role();
		
		// 8. 뽑아낸 롤이 Auth.Role.USER 인 경우
		// 	  인증된 모든 사용자는 접근 가능
//		if( role == Auth.Role.USER) {
//			return true;
//		}
		
		// 9. Admin Role 권한 체크
		//    AuthUser.getRole().equals("ADMIN")
		
		return true;
	}
	

	
}