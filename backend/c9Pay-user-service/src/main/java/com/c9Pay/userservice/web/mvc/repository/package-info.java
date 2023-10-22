/**
 * 이 패키지는 데이터베이스와의 상호 작용을 관리하는 리포지토리 클래스들을 포함하고 있다.
 * 리포지토리 클래스들은 데이터베이스 CRUD (생성, 조회, 갱신, 삭제) 작업을 수행하며,
 * 데이터 영속성을 관리하고 비즈니스 로직에서 필요한 데이터를 제공한다.
 * <p>
 * 데이터베이스 쿼리와 관련된 작업들은 이 패키지 내에서 처리되며, 서비스 계층이나 컨트롤러에게
 * 필요한 데이터를 가져올 수 있도록 한다.
 * <p>
 * 리포지토리 클래스들은 데이터베이스와의 효율적인 통신을 담당하며, 데이터베이스에 대한 변경이나
 * 새로운 쿼리를 추가할 때 유지 보수가 용이하도록 구조화된다.
 */
package com.c9Pay.userservice.web.mvc.repository;