# 1. 프로젝트 소개

## 개요
2022 기준 전체 약 59%의결제가 모바일을 통해 이루어질 정도로 전자 결제가 크게 증가했다.그리고 다양한 서비스의 등장과 규제 완화로 결제 방식 또한 빠르게 변화하고 있다.이러한 상황에 맞춰 자유로운 확장이 가능한 클라우드 환경에 유연성이 장점인 MSA 설계를 적용하여 크레딧 결제 플랫폼을 구현하고자 한다.

##  과제 목표
• 클라우드 환경의 장점인 자유로운 서비스 확장을 고려한 크레딧 거래 플랫폼 개발
• Microservice Architecture 설계를적용하여 확장성과 유연성 확보 
• 컨테이너 오케스트레이션 기술인 kubernetes를 사용하여 운영

# 2. 팀 소개
| 이름   | 이메일 | 역할 |
| ------ | ------ | ---- |
| 김민승 |     kminssk@gmail.com  |   프론트엔드 개발   |
| 김민종 |   koust6u@pusan.ac.kr     |   백엔드 개발   |
| 송재원       |    fosong98@gmail.com    |   클라우드 개발   |
# 3. 구성도
## 시스템 맥락도
![전체 시스템 컨텍스트 다이어그램](https://github.com/pnucse-capstone/capstone-2023-1-24/assets/79358032/b64c6463-9f0a-45b4-96d9-06d0a43da87e)
- 3개의 클라이언트와 4개의 서비스로 시스템을 구성
- 유연한 확장을 위한 MSA 설계
- 자유로운 Scale-Out을 위해 무상태로 구현

## CI/CD 파이프라인
![파이프라인](https://github.com/pnucse-capstone/capstone-2023-1-24/assets/79358032/f74c372a-a6c1-4a0d-bcdf-a5a05435a867)
* Github webhook을 통한 자동 배포 시작
- Jenkins Dynamic Agent를 이용하여 병렬적으로 빌드 수행
- 빠른 배포 및 복원, 변경이력 관리, 편리한 클러스터 인프라 구성 공유를 위한 GitOps 방식 적용

## k8s 클러스터 개요도
![클러스터 개요도](https://github.com/pnucse-capstone/capstone-2023-1-24/assets/79358032/7bdc924e-7813-45aa-9691-908ad3550f9b)
- Spring Cloud Eureka를 사용하여 서비스 디스커버리 및 로드밸런싱
- 파드별 CPU 사용량을 기준으로 Autoscaling 적용
- Prometheus로 클러스터의 리소스를 모니터링하고 Zipkin으로 트랜잭션 흐름 모니터링
# 4. 소개 및 시연 영상
[![2023년 전기 졸업과제 24 클라우드나인](https://img.youtube.com/vi/SdUjcxSy9Gw/mqdefault.jpg)](https://www.youtube.com/watch?v=SdUjcxSy9Gw)
# 5. 사용법
