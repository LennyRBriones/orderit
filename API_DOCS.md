# OrderIt API Documentation

**Base URL:** `https://orderit-806794513763.us-east1.run.app`

---

## Waiters

### GET /waiters
Returns all waiters.
```json
// Response: 200
[
  { "id": 1, "name": "Juan", "username": "juan1" }
]
```

### GET /waiters/{id}
Returns a single waiter.
```json
// Response: 200
{ "id": 1, "name": "Juan", "username": "juan1" }
```

### POST /waiters
Creates a new waiter.
```json
// Request body
{ "name": "Juan", "username": "juan1", "password": "123" }

// Response: 201
{ "id": 1, "name": "Juan", "username": "juan1" }
```

### PUT /waiters/{id}
Updates a waiter.
```json
// Request body
{ "name": "Juan Updated", "username": "juan1", "password": "456" }

// Response: 200
{ "id": 1, "name": "Juan Updated", "username": "juan1" }
```

### DELETE /waiters/{id}
Deletes a waiter.
```
// Response: 200 (no body)
```

> Note: The password is never returned in responses.

---

## Categories

### GET /categories
```json
// Response: 200
[
  { "id": 1, "name": "Burgers" }
]
```

### GET /categories/{id}
```json
// Response: 200
{ "id": 1, "name": "Burgers" }
```

### POST /categories
```json
// Request body
{ "name": "Burgers" }

// Response: 201
{ "id": 1, "name": "Burgers" }
```

### PUT /categories/{id}
```json
// Request body
{ "name": "Pizzas" }

// Response: 200
{ "id": 1, "name": "Pizzas" }
```

### DELETE /categories/{id}
```
// Response: 200 (no body)
```

---

## Products

### GET /products
Returns all products.
```json
// Response: 200
[
  {
    "id": 1,
    "name": "Classic Burger",
    "description": "Beef patty with lettuce and tomato",
    "imageUrl": "https://example.com/burger.jpg",
    "categoryId": 1,
    "type": "individual",
    "price": 129.99,
    "salePrice": null,
    "saleStart": null,
    "saleEnd": null,
    "stock": 50,
    "available": true,
    "featured": false
  }
]
```

### GET /products/{id}
Returns a single product.

### GET /categories/{categoryId}/products
Returns all products for a specific category.
```json
// Response: 200
[
  { "id": 1, "name": "Classic Burger", "categoryId": 1, ... }
]
```

### POST /products
```json
// Request body (only name, categoryId, price are required)
{
  "name": "Classic Burger",
  "description": "Beef patty with lettuce and tomato",
  "imageUrl": "https://example.com/burger.jpg",
  "categoryId": 1,
  "type": "individual",
  "price": 129.99,
  "salePrice": 99.99,
  "saleStart": "2026-03-01T00:00:00",
  "saleEnd": "2026-03-31T23:59:59",
  "stock": 50,
  "available": true,
  "featured": true
}

// Response: 201
{ "id": 1, "name": "Classic Burger", ... }
```

#### Field defaults:
| Field | Default |
|-------|---------|
| description | "" |
| imageUrl | "" |
| type | "individual" |
| salePrice | null |
| saleStart | null |
| saleEnd | null |
| stock | 0 |
| available | true |
| featured | false |

### PUT /products/{id}
Same body as POST. Response: 200.

### DELETE /products/{id}
```
// Response: 200 (no body)
```

---

## Dining Tables

### GET /tables
```json
// Response: 200
[
  { "id": 1, "number": 1, "capacity": 4, "status": "free" }
]
```

### GET /tables/{id}
```json
// Response: 200
{ "id": 1, "number": 1, "capacity": 4, "status": "free" }
```

### POST /tables
```json
// Request body
{ "number": 1, "capacity": 4, "status": "free" }

// Response: 201
{ "id": 1, "number": 1, "capacity": 4, "status": "free" }
```

### PUT /tables/{id}
```json
// Request body
{ "number": 1, "capacity": 6, "status": "occupied" }

// Response: 200
{ "id": 1, "number": 1, "capacity": 6, "status": "occupied" }
```

### DELETE /tables/{id}
```
// Response: 200 (no body)
```

#### Status values:
- `"free"` - Available
- `"occupied"` - In use
- `"reserved"` - Reserved

---

## Orders

### GET /orders
```json
// Response: 200
[
  {
    "id": 1,
    "tableId": 1,
    "waiterId": 1,
    "guestCount": 4,
    "status": "new",
    "total": 0.0,
    "tip": 0.0,
    "openedAt": "2026-03-06T23:30:00",
    "closedAt": null
  }
]
```

### GET /orders/{id}
Returns a single order.

### POST /orders
```json
// Request body
{ "tableId": 1, "waiterId": 1, "guestCount": 4 }

// Response: 201
{
  "id": 1,
  "tableId": 1,
  "waiterId": 1,
  "guestCount": 4,
  "status": "new",
  "total": 0.0,
  "tip": 0.0,
  "openedAt": "2026-03-06T23:30:00",
  "closedAt": null
}
```

> `openedAt` is set automatically by the server. `status` defaults to `"new"`.

### PUT /orders/{id}
```json
// Request body (use this to update status, total, tip, etc.)
{ "tableId": 1, "waiterId": 1, "guestCount": 4 }

// Response: 200
{ "id": 1, ... }
```

### DELETE /orders/{id}
```
// Response: 200 (no body)
```

#### Status values:
- `"new"` - Just created
- `"in_preparation"` - Being prepared in kitchen
- `"served"` - Food delivered to table
- `"paid"` - Payment received
- `"cancelled"` - Order cancelled

---

## Order Details (items in an order)

### GET /orders/{orderId}/details
Returns all items for a specific order.
```json
// Response: 200
[
  {
    "id": 1,
    "orderId": 1,
    "productId": 3,
    "quantity": 2,
    "unitPrice": 129.99,
    "subtotal": 259.98
  }
]
```

### POST /orders/{orderId}/details
Adds an item to an order. The server automatically looks up the product price and calculates `unitPrice` and `subtotal`.
```json
// Request body
{ "orderId": 1, "productId": 3, "quantity": 2 }

// Response: 201
{
  "id": 1,
  "orderId": 1,
  "productId": 3,
  "quantity": 2,
  "unitPrice": 129.99,
  "subtotal": 259.98
}
```

> `unitPrice` and `subtotal` are calculated by the server based on the product's current price.

### DELETE /orders/{orderId}/details/{id}
Removes an item from an order.
```
// Response: 200 (no body)
```

> There is no PUT for order details. To change quantity, delete and re-add.

---

## Error Responses

### 400 Bad Request
```json
// Missing or invalid body
"Invalid or missing request body"
```

### 404 Not Found
```json
// Resource doesn't exist
"Waiter not found"
"Product not found"
"Order not found"
// etc.
```

### 500 Internal Server Error
```json
"500: <error message>"
```

---

## Android Integration Example (Retrofit)

### 1. Add dependencies (build.gradle.kts)
```kotlin
// Network
implementation("com.squareup.retrofit2:retrofit:2.11.0")
implementation("com.squareup.retrofit2:converter-kotlinx-serialization:2.11.0")
implementation("com.squareup.okhttp3:okhttp:4.12.0")
implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")
```

### 2. Data classes
```kotlin
@Serializable
data class CategoryRequest(val name: String)

@Serializable
data class CategoryResponse(val id: Int, val name: String)

@Serializable
data class ProductRequest(
    val name: String,
    val description: String = "",
    val imageUrl: String = "",
    val categoryId: Int,
    val type: String = "individual",
    val price: Double,
    val salePrice: Double? = null,
    val saleStart: String? = null,
    val saleEnd: String? = null,
    val stock: Int = 0,
    val available: Boolean = true,
    val featured: Boolean = false
)

@Serializable
data class ProductResponse(
    val id: Int,
    val name: String,
    val description: String,
    val imageUrl: String,
    val categoryId: Int,
    val type: String,
    val price: Double,
    val salePrice: Double?,
    val saleStart: String?,
    val saleEnd: String?,
    val stock: Int,
    val available: Boolean,
    val featured: Boolean
)

@Serializable
data class WaiterRequest(val name: String, val username: String, val password: String)

@Serializable
data class WaiterResponse(val id: Int, val name: String, val username: String)

@Serializable
data class DiningTableRequest(val number: Int, val capacity: Int, val status: String = "free")

@Serializable
data class DiningTableResponse(val id: Int, val number: Int, val capacity: Int, val status: String)

@Serializable
data class OrderRequest(val tableId: Int, val waiterId: Int, val guestCount: Int)

@Serializable
data class OrderResponse(
    val id: Int,
    val tableId: Int,
    val waiterId: Int,
    val guestCount: Int,
    val status: String,
    val total: Double,
    val tip: Double,
    val openedAt: String,
    val closedAt: String?
)

@Serializable
data class OrderDetailRequest(val orderId: Int, val productId: Int, val quantity: Int)

@Serializable
data class OrderDetailResponse(
    val id: Int,
    val orderId: Int,
    val productId: Int,
    val quantity: Int,
    val unitPrice: Double,
    val subtotal: Double
)
```

### 3. Retrofit API interface
```kotlin
interface OrderItApi {

    // Waiters
    @GET("waiters")
    suspend fun getWaiters(): List<WaiterResponse>

    @GET("waiters/{id}")
    suspend fun getWaiter(@Path("id") id: Int): WaiterResponse

    @POST("waiters")
    suspend fun createWaiter(@Body request: WaiterRequest): WaiterResponse

    @PUT("waiters/{id}")
    suspend fun updateWaiter(@Path("id") id: Int, @Body request: WaiterRequest): WaiterResponse

    @DELETE("waiters/{id}")
    suspend fun deleteWaiter(@Path("id") id: Int)

    // Categories
    @GET("categories")
    suspend fun getCategories(): List<CategoryResponse>

    @POST("categories")
    suspend fun createCategory(@Body request: CategoryRequest): CategoryResponse

    @PUT("categories/{id}")
    suspend fun updateCategory(@Path("id") id: Int, @Body request: CategoryRequest): CategoryResponse

    @DELETE("categories/{id}")
    suspend fun deleteCategory(@Path("id") id: Int)

    // Products
    @GET("products")
    suspend fun getProducts(): List<ProductResponse>

    @GET("products/{id}")
    suspend fun getProduct(@Path("id") id: Int): ProductResponse

    @GET("categories/{categoryId}/products")
    suspend fun getProductsByCategory(@Path("categoryId") categoryId: Int): List<ProductResponse>

    @POST("products")
    suspend fun createProduct(@Body request: ProductRequest): ProductResponse

    @PUT("products/{id}")
    suspend fun updateProduct(@Path("id") id: Int, @Body request: ProductRequest): ProductResponse

    @DELETE("products/{id}")
    suspend fun deleteProduct(@Path("id") id: Int)

    // Dining Tables
    @GET("tables")
    suspend fun getTables(): List<DiningTableResponse>

    @POST("tables")
    suspend fun createTable(@Body request: DiningTableRequest): DiningTableResponse

    @PUT("tables/{id}")
    suspend fun updateTable(@Path("id") id: Int, @Body request: DiningTableRequest): DiningTableResponse

    @DELETE("tables/{id}")
    suspend fun deleteTable(@Path("id") id: Int)

    // Orders
    @GET("orders")
    suspend fun getOrders(): List<OrderResponse>

    @GET("orders/{id}")
    suspend fun getOrder(@Path("id") id: Int): OrderResponse

    @POST("orders")
    suspend fun createOrder(@Body request: OrderRequest): OrderResponse

    @PUT("orders/{id}")
    suspend fun updateOrder(@Path("id") id: Int, @Body request: OrderRequest): OrderResponse

    @DELETE("orders/{id}")
    suspend fun deleteOrder(@Path("id") id: Int)

    // Order Details
    @GET("orders/{orderId}/details")
    suspend fun getOrderDetails(@Path("orderId") orderId: Int): List<OrderDetailResponse>

    @POST("orders/{orderId}/details")
    suspend fun createOrderDetail(
        @Path("orderId") orderId: Int,
        @Body request: OrderDetailRequest
    ): OrderDetailResponse

    @DELETE("orders/{orderId}/details/{id}")
    suspend fun deleteOrderDetail(@Path("orderId") orderId: Int, @Path("id") id: Int)
}
```

### 4. Retrofit instance
```kotlin
object RetrofitClient {

    private const val BASE_URL = "https://orderit-806794513763.us-east1.run.app/"

    private val json = Json { ignoreUnknownKeys = true }

    val api: OrderItApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(OrderItApi::class.java)
    }
}
```

### 5. Usage in ViewModel
```kotlin
class MenuViewModel : ViewModel() {

    private val api = RetrofitClient.api

    private val _categories = MutableStateFlow<List<CategoryResponse>>(emptyList())
    val categories: StateFlow<List<CategoryResponse>> = _categories.asStateFlow()

    fun loadCategories() {
        viewModelScope.launch {
            try {
                _categories.value = api.getCategories()
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}
```
