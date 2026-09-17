package com.example.lab10.service;

import com.example.lab10.model.Product;
import com.example.lab10.repository.ProductRepository;

import java.util.UUID;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * ProductService — Business Logic Layer
 *
 * ✅ @Service, Constructor Injection ครบแล้ว (DIP — SOLID)
 * ❌ TODO: เติม method body ให้ครบทุก method
 *
 * หน้าที่: รับ request จาก Controller → เรียก Repository → คืนผล
 * (SRP — แต่ละ class มีหน้าที่เดียว)
 *
 * Hint Operators ที่ควรใช้:
 *   .map(p -> ...)            แปลงค่า
 *   .flatMap(p -> ...)        async transform
 *   .defaultIfEmpty(...)      fallback ถ้าว่าง
 *   .switchIfEmpty(Mono...)   fallback Mono ถ้าว่าง
 */
@Service
public class ProductService {

    // ── Constructor Injection (DIP — SOLID) ─────────────
    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    // ── 1. ดึง Product 1 รายการ ──────────────────────────
    /**
     * TODO: เรียก repository.findById(id) แล้วคืนผล
     *       ถ้าไม่พบให้ throw RuntimeException("Product not found: " + id)
     *
     * Hint: repository.findById(id)
     *       .switchIfEmpty(Mono.error(new RuntimeException(...)))
     */
    public Mono<Product> getById(String id) {
        // TODO: เติม code ตรงนี้

        return repository.findById(id).switchIfEmpty( Mono.error(new RuntimeException("Product not found: " + id))); // ← แก้บรรทัดนี้

        // Getbyid ไปเรียกจาก repository หาก Repository คืน Mono.empty() จะใช้ switchIfEmpty() เปลี่ยนเป็น error
    }

    // ── 2. ดึง Product ทั้งหมด ───────────────────────────
    /**
     * TODO: เรียก repository.findAll() แล้วคืนผล
     */
    public Flux<Product> getAll() {
        // TODO: เติม code ตรงนี้
        Flux<Product> productFlux = repository.findAll();
        return productFlux; // ← แก้บรรทัดนี้

        // ขอสินค้าทั้งหมดคืนเป็น Flux เพราะอาจมี0ถึงหลายรายการ
    }

    // ── 3. บันทึก Product ────────────────────────────────
    /**
     * TODO: เรียก repository.save(product) แล้วคืนผล
     *
     * เพิ่มเติม: ถ้า product.getId() เป็น null ให้ generate id ใหม่
     * Hint: java.util.UUID.randomUUID().toString()
     */
    public Mono<Product> save(Product product) {
        // TODO: เติม code ตรงนี้
        String productId = product.getId();
        if (productId == null) {
            UUID generatedUuid = UUID.randomUUID();
            String generatedId = generatedUuid.toString();
            product.setId(generatedId);
        }

        Mono<Product> savedProductMono = repository.save(product);
        return savedProductMono; // ← แก้บรรทัดนี้

        // บันทึกสินค้าโดยถ้าสินค้าไม่มี ID จะสร้าง UUID ก่อนบันทึก แล้วคืนสินค้าที่บันทึกแล้วเป็น Mono<Produtc>
    }

    // ── 4. ลบ Product ────────────────────────────────────
    /**
     * TODO: เรียก repository.deleteById(id) แล้วคืนผล
     */
    public Mono<Void> delete(String id) {
        // TODO: เติม code ตรงนี้
        Mono<Void> deleteResultMono = repository.deleteById(id);

        return deleteResultMono; // ← แก้บรรทัดนี้

        // ส่วนนี้ก็ลบสินค้าโดยใช้ไอดี  แล้วก็คืนเป็น Mono<Void> เพราะไม่มีข้อมูลตอบกลับ
    }

    // ── 5. กรองตาม category ──────────────────────────────
    /**
     * TODO: เรียก repository.findByCategory(category) แล้วคืนผล
     */
    public Flux<Product> getByCategory(String category) {
        // TODO: เติม code ตรงนี้
        Flux<Product> matchingProducts = repository.findByCategory(category);

        return matchingProducts; // ← แก้บรรทัดนี้

        // ส่วนนี้หาสินค้าโดยใช้ category ก็คืนเป็น Flux<Product> เพราะอาจมีหลายรายการ
    }

    // ── 6. คำนวณราคาหลังส่วนลด ───────────────────────────
    /**
     * TODO: หา Product จาก id แล้วคืน discountedPrice
     *
     * Hint: getById(id)
     *       .map(p -> p.getDiscountedPrice())
     */
    public Mono<Double> getDiscountedPrice(String id) {
        // TODO: เติม code ตรงนี้
        Mono<Product> productMono = getById(id);
        Mono<Double> discountedPriceMono = productMono.map(product -> {
                Double discountedPrice = product.getDiscountedPrice();
                return discountedPrice;
            });
        return discountedPriceMono; // ← แก้บรรทัดนี้

        // ส่วนนี้จะหาราคาหลังลดแล้วก็จะไปหาสินค้าโดยใช้ไอดีก่อนแล้วค่อยคำนวณแล้วส่งออกเป็น Mono<Double> เป็นราคาที่ลดแล้ว
    }
}
