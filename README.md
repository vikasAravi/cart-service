# Recipe Management System

## Overview
This project is a **Recipe Management System** that allows users to manage recipes, products, carts, and cart items. The application is built using **Spring Boot** and **Hibernate**, and it connects to a **MySQL** database.

## Prerequisites
- **Java 17 or higher**
- **Gradle**
- **MySQL Server**

## Database Setup

### MySQL Installation
Ensure that you have MySQL installed and running on your machine. If not, download and install it from the [MySQL official website](https://dev.mysql.com/downloads/mysql/).

### Database Configuration
Before running the application, configure the `application.properties` file with the following database connection details:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/recipe
spring.datasource.username=root
spring.datasource.password=VikashVarma@1999

Note: Change the attributes according to your MySQL installation settings if necessary:

spring.datasource.url: Adjust the hostname and database name if different.
spring.datasource.username: Update if you use a different MySQL user.
spring.datasource.password: Change the password as per your configuration.
SQL DDL Statements
Execute the following SQL commands to create the necessary tables in your MySQL database:
```

```
Create products Table
CREATE TABLE products (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price_in_cents INT NOT NULL DEFAULT 0,
    stock_quantity INT NOT NULL DEFAULT 0,
    category VARCHAR(255),
    subcategory VARCHAR(255),
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    brand VARCHAR(255),
    weight_in_grams DECIMAL(10, 2),
    dimensions VARCHAR(255),
    image_url VARCHAR(255)
);
```

```
Create recipes Table
CREATE TABLE recipes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price_in_cents INT,
    instructions TEXT NOT NULL,
    prep_time_minutes INT,
    cook_time_minutes INT,
    total_time_minutes INT,
    difficulty_level VARCHAR(50),
    created_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(255),
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    last_updated TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    cuisine_type VARCHAR(255),
    calories_per_serving INT,
    tags JSON,
    image_url VARCHAR(255),
    video_url VARCHAR(255),
    rating DECIMAL(5, 2),
    review_count INT DEFAULT 0
);
```

```
Create recipe_products Table
CREATE TABLE recipe_products (
    id INT AUTO_INCREMENT PRIMARY KEY,
    recipe_id INT,
    product_id INT,
    is_optional BOOLEAN NOT NULL DEFAULT FALSE,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (recipe_id) REFERENCES recipes(id),
    FOREIGN KEY (product_id) REFERENCES products(id)
);
```

```
Create carts Table
CREATE TABLE carts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    total_in_cents INT NOT NULL DEFAULT 0,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

```
Create cart_items Table
CREATE TABLE cart_items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cart_id INT NOT NULL,
    product_id INT,
    recipe_id INT,
    quantity INT NOT NULL DEFAULT 1,
    type VARCHAR(50) NOT NULL DEFAULT 'RECIPE',
    FOREIGN KEY (cart_id) REFERENCES carts(id),
    FOREIGN KEY (product_id) REFERENCES products(id),
    FOREIGN KEY (recipe_id) REFERENCES recipes(id)
);
```

```
Dummy Data Insertion - To insert a dummy cart record, run the following SQL command:
INSERT INTO carts (total_in_cents, created_at, updated_at) 
VALUES (1000, NOW(), NOW());
```

```
Running the Application
Clone the repository:

git clone <repository-url>
cd <project-directory>
Build the project: ./gradlew build
Run the application: ./gradlew bootRun
```

```
If you are facing gradle permission errors, please try this.


The "permission denied" error typically indicates that the gradlew file does not have executable permissions. You can resolve this issue by changing the file permissions. Here’s how to do it:

Open your terminal.

Navigate to your project directory.

cd <project-directory>

Change the permissions to make gradlew executable:
chmod +x gradlew
Now try running the command again gradle commands

```

