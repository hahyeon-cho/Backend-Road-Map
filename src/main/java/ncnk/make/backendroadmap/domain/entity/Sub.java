package ncnk.make.backendroadmap.domain.entity;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public enum Sub {
    IP("IP", 1, 1, "[Internet protocol]\n네트워크를 통해 데이터를 보내는 데 사용되는 프로토콜."),
    TCP_UDP("TCP/UDP", 1, 2,
            "[Transmission Control Protocol]\n[User Datagram Protocol]\n네트워크를 통해 데이터를 전송하는 데 사용되는 전송 계층 프로토콜. TCP는 신뢰성 있는 전송을, UDP는 빠른 전송을 제공."),
    PORT("PORT", 1, 3, "통신을 위한 운영 체제의 포트."),
    DNS("DNS", 1, 4, "[Domain Name System]\n도메인 이름을 IP 주소로 변환하여 웹 사이트에 액세스하는 데 사용되는 시스템."),
    HTTP("HTTP", 1, 5, "[HyperText Transfer Protocol]\n웹에서 데이터를 전송하는 데 사용되는 프로토콜."),
    COOKIE("Cookie", 1, 6, "웹 브라우저에 저장되는 작은 데이터 조각으로, 사용자 정보를 추적하거나 상태를 유지하는 데 사용."),
    CACHE("Cache", 1, 7, "데이터를 일시적으로 저장하여 빠른 액세스를 제공하는 장치나 소프트웨어."),
    HTML("HTML", 2, 1, "[Hypertext Markup Language]\n웹 페이지를 만드는 데 사용되는 마크업 언어."),
    CSS("CSS", 2, 2, "[Cascading Style Sheets]\nHTML 문서의 스타일을 정의하는 데 사용되는 스타일 시트 언어."),
    BASIC_JS("JS ", 2, 3, "[JavaScript]\n웹 페이지 내에서 상호 작용을 구현하는 데 사용되는 프로그래밍 언어."),
    OS_WORK("OS work", 3, 1, "운영 체제의 기능과 작동 방식."),
    PROCESS("Process 관리", 3, 2, "운영 체제에서 프로세스를 생성, 제어 및 관리하는 활동."),
    THREADS_CONCURRENTCY("Threads & Concurrentcy", 3, 3, "프로그램 내에서 동시에 여러 작업을 수행하는 기능."),
    BASIC_TERMAIN_COMMANDS("Basic Termain Commands", 3, 4, "명령 줄 인터페이스를 통해 시스템과 상호 작용하기 위한 명령어."),
    MEMORY_MANAGE("Memory 관리", 3, 5, "컴퓨터 메모리의 할당과 해제를 관리하는 프로세스."),
    IPC("IPC (Inter Process Communication)", 3, 6, "[Inter Process Communication]\n운영 체제에서 프로세스 간 데이터 교환을 위한 메커니즘."),
    INPUT_OUTPUT("I/O 관리", 3, 7, "[Input and Output]\n입력 및 출력 장치와의 상호 작용을 관리하는 작업."),
    BASIC_NETWORK("기본 네트워크 개념", 3, 8, "컴퓨터 네트워킹의 기본 개념과 원리."),
    JAVA("Java", 4, 1, "객체 지향 프로그래밍 언어로, 다양한 플랫폼에서 사용."),
    C_SHAP("C# (Posix Basic)", 4, 2, "마이크로소프트에서 개발한 프로그래밍 언어로, .NET 프레임워크에서 사용."),
    PYTHON("Python", 4, 3, "간결하고 읽기 쉬운 문법을 가진 고수준 프로그래밍 언어."),
    PHP("PHP", 4, 4, "[PHP: Hypertext Preprocessor]\n서버 측 웹 개발을 위한 스크립팅 언어."),
    RUBY("Ruby", 4, 5, "간결하고 생산적인 문법을 가진 동적 프로그래밍 언어."),
    GO("Go", 4, 6, "구글에서 개발한 컴파일 언어로, 간결하고 효율적인 코드를 작성 가능."),
    RUST("Rust", 4, 7, "안전하고 병행성을 지원하는 시스템 프로그래밍 언어."),
    JAVASCRIPT("JS", 4, 8, "[JavaScript]\n웹 페이지 내에서 상호 작용을 구현하는 데 사용되는 프로그래밍 언어."),
    BUBBLE_SORT("Bubble Sort", 5, 1, "인접한 두 요소를 비교하여 정렬하는 간단한 정렬 알고리즘."),
    SELECTION_SORT("Selection Sort", 5, 2, "가장 작은(또는 가장 큰) 요소를 선택하여 정렬하는 알고리즘."),
    INSERTION_SORT("Insertion Sort", 5, 3, "각 요소를 적절한 위치에 삽입하여 정렬하는 알고리즘."),
    QUICK_SORT("Quick Sort", 5, 4, "피벗을 기준으로 리스트를 분할하고 정렬하는 분할 정복 알고리즘."),
    MERGE_SORT("Merge Sort", 5, 5, "분할 정복 방식으로 리스트를 분할하고 병합하여 정렬하는 알고리즘."),
    HEAP_SORT("Heap Sort", 5, 6, "구글에서 개발한 컴파일 언어로, 간결하고 효율적인 코드를 작성 가능."),
    RADIX_SORT("Radix Sort", 5, 7, "안전하고 병행성을 지원하는 시스템 프로그래밍 언어."),
    COUNTING_SORT("Counting Sort", 5, 8, "카운트 배열을 사용하여 정렬하는 알고리즘."),
    BINARY_SEARCH("Binary Search", 5, 9, "정렬된 배열에서 원하는 요소를 찾는 알고리즘."),
    HASH_TABLE("Hash Table", 5, 10, "키-값 쌍을 저장하는 데이터 구조."),
    DFS_BFS("DFS & BFS", 5, 11, "[Depth-First Search & Breadth-First Search]\n그래프를 탐색하는 두 가지 주요 알고리즘."),
    LIS("LIS(최장 증가 수열)", 5, 12, "[Longest Increasing Subsequence]\n서버 측 웹 개발을 위한 스크립팅 언어."),
    LCA("LCA(최소 공통 조상)", 5, 13, "[Lowest Common Ancestor]\n트리에서 두 노드의 가장 가까운 공통 조상을 찾는 알고리즘."),
    DP("DP(동적 계획법)", 5, 14, "[Dynamic Programming]\n복잡한 문제를 간단한 하위 문제로 분할하여 효율적으로 해결하는 알고리즘."),
    DIJKSTRA("Dijkstra(다익스트라)", 5, 15, "가중 그래프에서 출발점부터 모든 노드까지의 최단 경로를 찾는 알고리즘."),
    BITMASK("BitMask(비트마스크)", 5, 16, "이진수를 사용하여 집합을 나타내고 연산하는 기법."),
    USING_GIT("Git 사용법", 6, 1, "미완성"),
    GITHUB("GitHub", 7, 1, "미완성"),
    GITLAB("Gitlab", 7, 2, "미완성"),
    POSTGRESQL("PostgreSQL", 8, 1, "미완성"),
    MYSQL("MySQL", 8, 2, "미완성"),
    ORACLE("Oracle", 8, 3, "미완성"),
    DYNAMODB("DynamoDB", 9, 1, "미완성"),
    TRANSACTION("Transaction", 10, 1, "미완성"),
    ACID("ACID", 10, 2, "미완성"),
    ORM("ORM", 10, 3, "미완성"),
    N_PLUS_ONE("N + 1", 10, 4, "미완성"),
    AUTHENTICATION("Authentication", 11, 1, "미완성"),
    REST("REST", 11, 2, "미완성"),
    JSON_API("JSON API", 11, 3, "미완성"),
    SOAP("SOAP", 11, 4, "미완성"),
    SPRING("Spring", 12, 1, "미완성"),
    DJANGO("Django", 12, 2, "미완성"),
    CDN("CDN", 13, 1, "미완성"),
    SERVER_SIDE("Server Side (Redis, Memcached)", 13, 2, "미완성"),
    CLIENT_SIDE("Client Side", 13, 3, "미완성"),
    HTTPS("HTTPS", 14, 1, "미완성"),
    CORS("CORS", 14, 2, "미완성"),
    SSL_TLS("SSL/TLS", 14, 3, "미완성"),
    INTEGRATION_TEST("Integration Test", 15, 1, "미완성"),
    UNIT_TEST("Unit Test", 15, 2, "미완성"),
    FUNCTIONAL_TEST("Functional Test", 15, 3, "미완성"),
    JENKINS("젠킨스", 16, 1, "미완성"),
    DESIGN_PATTERN("Design Pattern", 17, 1, "미완성"),
    GOF_DESIGN_PATTERN("GOF Design Pattern", 17, 2, "미완성"),
    DOMAIN_DRIVEN_DESIGN("Domain Driven Design", 17, 3, "미완성"),
    TEST_DRIVEN_DESIGN("Test Driven Design", 17, 4, "미완성"),
    SOLID("SOLID", 17, 5, "미완성"),
    ELASTIC_SEARCH("Elastic search", 18, 1, "미완성"),
    DOCKER("Docker", 19, 1, "미완성"),
    WEB_SERVER("Web server / WAS", 20, 1, "미완성"),
    NGINX("Nginx", 20, 2, "미완성");

    private final String subCategory;
    private final int mainCategoryNumber;
    private final int subDocsOrder;
    private final String subDescription;


    Sub(String subCategory, int mainCategoryNumber, int subDocsOrder, String subDescription) {
        this.subCategory = subCategory;
        this.mainCategoryNumber = mainCategoryNumber;
        this.subDocsOrder = subDocsOrder;
        this.subDescription = subDescription;
    }

    public static List<Sub> getOrderedSubDocsInCategory(int category) {
        return Arrays.stream(Sub.values())
                .filter(sub -> sub.getMainCategoryNumber() == category)
                .sorted(Comparator.comparingInt(Sub::getSubDocsOrder))
                .collect(Collectors.toList());
    }
}