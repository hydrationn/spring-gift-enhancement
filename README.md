# spring-gift-enhancement

## [STEP 0] 기본 코드 준비
- 이전 미션(위시 리스트-요청과 응답 심화)의 스켈레톤 코드를 기반으로 본격적인 개발 시작 전 환경 세팅

## [STEP 1] 엔티티 매핑
JdbcTemplate 기반 코드를 JPA로 리팩토링 하여 객체와 테이블을 매핑하도록 한다.
- Member 엔티티 매핑 및 테스트 코드 작성
- Product 엔티티 매핑
- Wish 엔티티 매핑 (연관관계 포함)
- @DataJpaTest 기반 Repository test code 작성

## [STEP 2] 페이지네이션
상품과 위시 리스트 보기에 페이지네이션을 구현한다.
- Product Pagination
- Member Pagination
- Wish Pagination (상풍명 오름차순/내림차순 정렬 가능)
