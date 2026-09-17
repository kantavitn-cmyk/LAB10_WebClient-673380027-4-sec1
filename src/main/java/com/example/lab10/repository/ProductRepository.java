package com.example.lab10.repository;

import com.example.lab10.model.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ProductRepository — In-memory Reactive Repository
 *
 * ✅ โครงสร้างและ annotation ครบแล้ว
 * ❌ TODO: เติม method body ให้ครบทุก method
 *
 * ใช้ ConcurrentHashMap เป็น in-memory storage
 * (ไม่ต่อ Database — เน้นฝึก Mono/Flux)
 *
 * Hint:
 *   - Mono.just(value)          คืนค่าเดียว
 *   - Mono.empty()              คืนเปล่า
 *   - Flux.fromIterable(list)   คืนหลายค่าจาก collection
 */
public class ProductRepository {

    // ── In-memory storage ────────────────────────────────
    private final Map<String, Product> store = new ConcurrentHashMap<>();

    // ── Constructor: ใส่ข้อมูลตัวอย่าง ──────────────────
    public ProductRepository() {
        store.put("1", new Product("1", "iPhone 15 Pro (673380027-4 SEC 1)",
                "Electronics", "Apple", 50, 39900.0, "MEMBER"));
        store.put("2", new Product("2", "MacBook Air M3",
                "Electronics", "Apple", 20, 49900.0, "NONE"));
        store.put("3", new Product("3", "Samsung Galaxy S24",
                "Electronics", "Samsung", 30, 29900.0, "SEASONAL"));
    }

    // ── 1. หา Product 1 รายการ ───────────────────────────
    /**
     * TODO: คืน Mono<Product> จาก store โดยใช้ id
     *       ถ้าไม่พบให้คืน Mono.empty()
     *
     * Hint: store.get(id) คืน Product หรือ null
     *       ถ้า null ให้ใช้ Mono.empty()
     *       ถ้ามีค่าให้ใช้ Mono.just(product)
     */
    public Mono<Product> findById(String id) {
        // TODO: เติม code ตรงนี้
        Product product = store.get(id);
        if(product == null){
            return Mono.empty();
        }
        return Mono.just(product); // ← แก้บรรทัดนี้

        // หาสินค้าโดยใช้ไอดีถ้าเจอก็คืน Mono.just(product) ไม่เจอก็จะคืน Mono.empty()
    }

    // ── 2. หา Product ทั้งหมด ────────────────────────────
    /**
     * TODO: คืน Flux<Product> ของทุกรายการใน store
     *
     * Hint: store.values() คืน Collection<Product>
     *       ใช้ Flux.fromIterable(...) แปลงเป็น Flux
     */
    public Flux<Product> findAll() {
        // Collection<Product> products = store.values();
        // Flux<Product> productFlux = Flux.fromIterable(products);
        // return productFlux;

        // TODO: เติม code ตรงนี้
        return Flux.fromIterable(store.values()); // ← แก้บรรทัดนี้

        // หรือสวนนี้ก็คือหาสินค้าทั้งหมดก็จะคืนเป็น Flux<Product>
    }

    // ── 3. บันทึก Product ────────────────────────────────
    /**
     * TODO: บันทึก product ลง store แล้วคืน Mono<Product>
     *
     * Hint: store.put(product.getId(), product)
     *       แล้วใช้ Mono.just(product) คืนค่า
     */
    public Mono<Product> save(Product product) {
        // TODO: เติม code ตรงนี้
        store.put(product.getId(), product);
        return Mono.just(product); // ← แก้บรรทัดนี้
        // ส่วนนี้ก็จะบันึกโดยใช้ ID เป็น key แล้วคืนสินค้าที่บันทึกแล้ว
    }

    // ── 4. ลบ Product ────────────────────────────────────
    /**
     * TODO: ลบ product จาก store แล้วคืน Mono<Void>
     *
     * Hint: store.remove(id)
     *       แล้วใช้ Mono.empty() คืนค่า (Mono<Void>)
     */
    public Mono<Void> deleteById(String id) {
        // TODO: เติม code ตรงนี้
        store.remove(id);
        return Mono.empty(); // ← แก้บรรทัดนี้
        // ลบสินค้าตาม ID และคืน Mono<Void> เพราะไม่มีข้อมูลตอบกลับ
    }

    // ── 5. กรองตาม category ──────────────────────────────
    /**
     * TODO: คืน Flux<Product> ที่ category ตรงกัน
     *
     * Hint: findAll()
     *       .filter(p -> p.getCategory().equalsIgnoreCase(category))
     */
    public Flux<Product> findByCategory(String category) {
        // TODO: เติม code ตรงนี้
        // Flux<Product> allProducts = findAll();
        // Flux<Product> filteredProducts = allProducts.filter(product -> {
        //     String productCategory = product.getCategory();
        //     boolean categoryMatches =
        //             productCategory.equalsIgnoreCase(category);

        //     return categoryMatches;
        // });
        // return filteredProducts;

        return findAll().filter(product -> product.getCategory().equalsIgnoreCase(category)); // ← แก้บรรทัดนี้

        // กรองสินค้าทุกชิ้นและคืนเฉพาะรายการที่ category ตรงกัน เป็น Flux<Product>
    }
}
