package com.rbme.apis.config;

import com.rbme.apis.entity.UserMenuAccess.Menu;
import com.rbme.apis.entity.UserMenuAccess.Permission;
import com.rbme.apis.entity.UserMenuAccess.Role;
import com.rbme.apis.entity.UserMenuAccess.User;
import com.rbme.apis.repository.UserMenuAccess.MenuRepository;
import com.rbme.apis.repository.UserMenuAccess.PermissionRepository;
import com.rbme.apis.repository.UserMenuAccess.RoleRepository;
import com.rbme.apis.repository.UserMenuAccess.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final MenuRepository menuRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        System.out.println("========================================");
        System.out.println("Starting RBME Data Initialization...");
        System.out.println("========================================");

        // =========================================================
        // 1. CREATE SYSTEM PERMISSIONS
        // =========================================================

        createPermissions();

        // =========================================================
        // 2. CREATE ADMIN ROLE
        // =========================================================

        Role adminRole = createAdminRole();

        // =========================================================
        // 3. ASSIGN ALL PERMISSIONS TO ADMIN
        // =========================================================

        assignPermissionsToAdmin(adminRole);

        // =========================================================
        // 4. CREATE DEFAULT ADMIN USER
        // =========================================================

        createDefaultAdmin(adminRole);

        // =========================================================
        // 5. CREATE SYSTEM MENUS
        // =========================================================

        createMenus();

        System.out.println("========================================");
        System.out.println("RBME Data Initialization Completed.");
        System.out.println("========================================");
    }

    // =========================================================
    // CREATE PERMISSIONS
    // =========================================================

    private void createPermissions() {

        // =====================================================
        // DASHBOARD
        // =====================================================

        createPermission(
                "DASHBOARD_VIEW",
                "View Dashboard",
                "Allows user to view dashboard"
        );

        // =====================================================
        // COMPANY
        // =====================================================

        createPermission(
                "COMPANY_VIEW",
                "View Companies",
                "Allows user to view companies"
        );

        createPermission(
                "COMPANY_CREATE",
                "Create Company",
                "Allows user to create companies"
        );

        createPermission(
                "COMPANY_EDIT",
                "Edit Company",
                "Allows user to edit companies"
        );

        createPermission(
                "COMPANY_DELETE",
                "Delete Company",
                "Allows user to delete companies"
        );

        // =====================================================
        // CATEGORY
        // =====================================================

        createPermission(
                "CATEGORY_VIEW",
                "View Categories",
                "Allows user to view categories"
        );

        createPermission(
                "CATEGORY_CREATE",
                "Create Category",
                "Allows user to create categories"
        );

        createPermission(
                "CATEGORY_EDIT",
                "Edit Category",
                "Allows user to edit categories"
        );

        createPermission(
                "CATEGORY_DELETE",
                "Delete Category",
                "Allows user to delete categories"
        );

        // =====================================================
        // PRODUCT TYPE
        // =====================================================

        createPermission(
                "PRODUCT_TYPE_VIEW",
                "View Product Types",
                "Allows user to view product types"
        );

        createPermission(
                "PRODUCT_TYPE_CREATE",
                "Create Product Type",
                "Allows user to create product types"
        );

        createPermission(
                "PRODUCT_TYPE_EDIT",
                "Edit Product Type",
                "Allows user to edit product types"
        );

        createPermission(
                "PRODUCT_TYPE_DELETE",
                "Delete Product Type",
                "Allows user to delete product types"
        );

        // =====================================================
        // PRODUCT
        // =====================================================

        createPermission(
                "PRODUCT_VIEW",
                "View Products",
                "Allows user to view products"
        );

        createPermission(
                "PRODUCT_CREATE",
                "Create Product",
                "Allows user to create products"
        );

        createPermission(
                "PRODUCT_EDIT",
                "Edit Product",
                "Allows user to edit products"
        );

        createPermission(
                "PRODUCT_DELETE",
                "Delete Product",
                "Allows user to delete products"
        );

        // =====================================================
        // PRODUCT SPECIFICATION
        // =====================================================

        createPermission(
                "PRODUCT_SPECIFICATION_VIEW",
                "View Product Specifications",
                "Allows user to view product specifications"
        );

        createPermission(
                "PRODUCT_SPECIFICATION_CREATE",
                "Create Product Specification",
                "Allows user to create product specifications"
        );

        createPermission(
                "PRODUCT_SPECIFICATION_EDIT",
                "Edit Product Specification",
                "Allows user to edit product specifications"
        );

        createPermission(
                "PRODUCT_SPECIFICATION_DELETE",
                "Delete Product Specification",
                "Allows user to delete product specifications"
        );

        // =====================================================
        // USER
        // =====================================================

        createPermission(
                "USER_VIEW",
                "View Users",
                "Allows user to view users"
        );

        createPermission(
                "USER_CREATE",
                "Create User",
                "Allows user to create users"
        );

        createPermission(
                "USER_EDIT",
                "Edit User",
                "Allows user to edit users"
        );

        createPermission(
                "USER_DELETE",
                "Delete User",
                "Allows user to delete users"
        );

        // =====================================================
        // ROLE
        // =====================================================

        createPermission(
                "ROLE_VIEW",
                "View Roles",
                "Allows user to view roles"
        );

        createPermission(
                "ROLE_CREATE",
                "Create Role",
                "Allows user to create roles"
        );

        createPermission(
                "ROLE_EDIT",
                "Edit Role",
                "Allows user to edit roles"
        );

        createPermission(
                "ROLE_DELETE",
                "Delete Role",
                "Allows user to delete roles"
        );

        // =====================================================
        // PERMISSION
        // =====================================================

        createPermission(
                "PERMISSION_VIEW",
                "View Permissions",
                "Allows user to view permissions"
        );

        createPermission(
                "PERMISSION_CREATE",
                "Create Permission",
                "Allows user to create permissions"
        );

        createPermission(
                "PERMISSION_EDIT",
                "Edit Permission",
                "Allows user to edit permissions"
        );

        createPermission(
                "PERMISSION_DELETE",
                "Delete Permission",
                "Allows user to delete permissions"
        );

        // =====================================================
        // MENU
        // =====================================================

        createPermission(
                "MENU_VIEW",
                "View Menus",
                "Allows user to view menus"
        );

        createPermission(
                "MENU_CREATE",
                "Create Menu",
                "Allows user to create menus"
        );

        createPermission(
                "MENU_EDIT",
                "Edit Menu",
                "Allows user to edit menus"
        );

        createPermission(
                "MENU_DELETE",
                "Delete Menu",
                "Allows user to delete menus"
        );

        // =====================================================
        // SALES
        // =====================================================

        createPermission(
                "SALE_VIEW",
                "View Sales",
                "Allows user to view sales"
        );

        createPermission(
                "SALE_CREATE",
                "Create Sale",
                "Allows user to create sales"
        );

        createPermission(
                "SALE_EDIT",
                "Edit Sale",
                "Allows user to edit sales"
        );

        createPermission(
                "SALE_DELETE",
                "Delete Sale",
                "Allows user to delete sales"
        );

        // =====================================================
        // QUOTATION
        // =====================================================

        createPermission(
                "QUOTATION_VIEW",
                "View Quotations",
                "Allows user to view quotations"
        );

        createPermission(
                "QUOTATION_CREATE",
                "Create Quotation",
                "Allows user to create quotations"
        );

        createPermission(
                "QUOTATION_EDIT",
                "Edit Quotation",
                "Allows user to edit quotations"
        );

        createPermission(
                "QUOTATION_DELETE",
                "Delete Quotation",
                "Allows user to delete quotations"
        );

        // =====================================================
        // SALES ORDER
        // =====================================================

        createPermission(
                "SALES_ORDER_VIEW",
                "View Sales Orders",
                "Allows user to view sales orders"
        );

        createPermission(
                "SALES_ORDER_CREATE",
                "Create Sales Order",
                "Allows user to create sales orders"
        );

        createPermission(
                "SALES_ORDER_EDIT",
                "Edit Sales Order",
                "Allows user to edit sales orders"
        );

        createPermission(
                "SALES_ORDER_DELETE",
                "Delete Sales Order",
                "Allows user to delete sales orders"
        );

        // =====================================================
        // SERVICE
        // =====================================================

        createPermission(
                "SERVICE_VIEW",
                "View Services",
                "Allows user to view services"
        );

        createPermission(
                "SERVICE_CREATE",
                "Create Service",
                "Allows user to create services"
        );

        createPermission(
                "SERVICE_EDIT",
                "Edit Service",
                "Allows user to edit services"
        );

        createPermission(
                "SERVICE_DELETE",
                "Delete Service",
                "Allows user to delete services"
        );
    }

    // =========================================================
    // CREATE PERMISSION
    // =========================================================

    private Permission createPermission(
            String code,
            String name,
            String description
    ) {

        return permissionRepository
                .findByCode(code)
                .orElseGet(() -> {

                    Permission permission = new Permission();

                    permission.setCode(code);
                    permission.setName(name);
                    permission.setDescription(description);
                    permission.setActive(true);

                    Permission saved =
                            permissionRepository.save(permission);

                    System.out.println(
                            "Permission created: " + code
                    );

                    return saved;
                });
    }

    // =========================================================
    // CREATE ADMIN ROLE
    // =========================================================

    private Role createAdminRole() {

        return roleRepository
                .findByName("ADMIN")
                .orElseGet(() -> {

                    Role role = new Role();

                    role.setName("ADMIN");

                    role.setDescription(
                            "System Administrator with full access"
                    );

                    role.setActive(true);

                    role.setPermissions(new HashSet<>());

                    Role saved =
                            roleRepository.save(role);

                    System.out.println(
                            "Role created: ADMIN"
                    );

                    return saved;
                });
    }

    // =========================================================
    // ASSIGN ALL PERMISSIONS TO ADMIN
    // =========================================================

    private void assignPermissionsToAdmin(
            Role adminRole
    ) {

        List<Permission> permissions =
                permissionRepository.findAll();

        adminRole.getPermissions().clear();

        adminRole.getPermissions().addAll(permissions);

        roleRepository.save(adminRole);

        System.out.println(
                "All permissions assigned to ADMIN."
        );
    }

    // =========================================================
    // CREATE DEFAULT ADMIN USER
    // =========================================================

    private void createDefaultAdmin(
            Role adminRole
    ) {

            if (!userRepository.existsByUsername("admin")) {

            User user = new User();

            user.setFullName("Super Admin");

            user.setUsername("admin");

            user.setEmail("admin@rbme.com");

            user.setPassword(
                    passwordEncoder.encode("Admin@123")
            );

            user.setRole(adminRole);

            user.setActive(true);

            userRepository.save(user);

            System.out.println(
                    "========================================"
            );

            System.out.println(
                    "Default Admin User Created."
            );

            System.out.println(
                    "Username: admin"
            );

            System.out.println(
                    "Password: Admin@123"
            );

            System.out.println(
                    "Role: ADMIN"
            );

            System.out.println(
                    "========================================"
            );

        } else {

            System.out.println(
                    "Default admin user already exists."
            );
        }
    }

    // =========================================================
    // CREATE MENUS
    // =========================================================

    private void createMenus() {

        // =====================================================
        // DASHBOARD
        // =====================================================

        createMenu(
                "DASHBOARD",
                "Dashboard",
                "bi bi-speedometer2",
                "/admin/dashboard",
                1,
                null,
                "DASHBOARD_VIEW"
        );

        // =====================================================
        // WEBSITE MANAGEMENT
        // =====================================================

        Menu websiteManagement = createMenu(
                "WEBSITE_MANAGEMENT",
                "Website Management",
                "bi bi-folder2-open",
                null,
                2,
                null,
                null
        );

        createMenu(
                "COMPANIES",
                "Companies",
                "bi bi-building",
                "/admin/company",
                1,
                websiteManagement,
                "COMPANY_VIEW"
        );

        createMenu(
                "CATEGORIES",
                "Categories",
                "bi bi-grid",
                "/admin/category",
                2,
                websiteManagement,
                "CATEGORY_VIEW"
        );

        createMenu(
                "PRODUCT_TYPES",
                "Product Types",
                "bi bi-diagram-3",
                "/admin/productType",
                3,
                websiteManagement,
                "PRODUCT_TYPE_VIEW"
        );

        createMenu(
                "PRODUCTS",
                "Products",
                "bi bi-box-seam",
                "/admin/product",
                4,
                websiteManagement,
                "PRODUCT_VIEW"
        );

        createMenu(
                "PRODUCT_SPECIFICATIONS",
                "Product Specifications",
                "bi bi-list-check",
                "/admin/product-specification",
                5,
                websiteManagement,
                "PRODUCT_SPECIFICATION_VIEW"
        );

        // =====================================================
        // ADMINISTRATION
        // =====================================================

        Menu administration = createMenu(
                "ADMINISTRATION",
                "Administration",
                "bi bi-shield-lock",
                null,
                3,
                null,
                null
        );

        createMenu(
                "USERS",
                "Users",
                "bi bi-people",
                "/admin/users",
                1,
                administration,
                "USER_VIEW"
        );

        createMenu(
                "ROLES",
                "Roles",
                "bi bi-shield-lock",
                "/admin/roles",
                2,
                administration,
                "ROLE_VIEW"
        );

        createMenu(
                "PERMISSIONS",
                "Permissions",
                "bi bi-key",
                "/admin/permissions",
                3,
                administration,
                "PERMISSION_VIEW"
        );

        createMenu(
                "MENUS",
                "Menus",
                "bi bi-list",
                "/admin/menus",
                4,
                administration,
                "MENU_VIEW"
        );
    }

    // =========================================================
    // CREATE / UPDATE MENU
    // =========================================================

    private Menu createMenu(
            String code,
            String name,
            String icon,
            String route,
            int displayOrder,
            Menu parent,
            String permissionCode
    ) {

        Menu menu =
                menuRepository
                        .findByCode(code)
                        .orElseGet(() -> {

                            Menu newMenu = new Menu();

                            newMenu.setCode(code);
                            newMenu.setName(name);
                            newMenu.setIcon(icon);
                            newMenu.setRoute(route);
                            newMenu.setDisplayOrder(displayOrder);
                            newMenu.setActive(true);
                            newMenu.setParent(parent);

                            if (permissionCode != null) {

                                Permission permission =
                                        permissionRepository
                                                .findByCode(
                                                        permissionCode
                                                )
                                                .orElse(null);

                                newMenu.setPermission(
                                        permission
                                );
                            }

                            Menu saved =
                                    menuRepository.save(newMenu);

                            System.out.println(
                                    "Menu created: " + code
                            );

                            return saved;
                        });

        boolean changed = false;

        // =====================================================
        // UPDATE BASIC DETAILS
        // =====================================================

        if (!name.equals(menu.getName())) {
            menu.setName(name);
            changed = true;
        }

        if (!icon.equals(menu.getIcon())) {
            menu.setIcon(icon);
            changed = true;
        }

        if (route == null
                ? menu.getRoute() != null
                : !route.equals(menu.getRoute())) {

            menu.setRoute(route);
            changed = true;
        }

        if (menu.getDisplayOrder() != displayOrder) {
            menu.setDisplayOrder(displayOrder);
            changed = true;
        }

        // =====================================================
        // UPDATE PARENT
        // =====================================================

        Long existingParentId =
                menu.getParent() != null
                        ? menu.getParent().getId()
                        : null;

        Long newParentId =
                parent != null
                        ? parent.getId()
                        : null;

        if (existingParentId == null
                ? newParentId != null
                : !existingParentId.equals(newParentId)) {

            menu.setParent(parent);
            changed = true;
        }

        // =====================================================
        // UPDATE PERMISSION
        // =====================================================

        Permission permission = null;

        if (permissionCode != null) {

            permission =
                    permissionRepository
                            .findByCode (permissionCode)
                            .orElse(null);
        }

        Long existingPermissionId =
                menu.getPermission() != null
                        ? menu.getPermission().getId()
                        : null;

        Long newPermissionId =
                permission != null
                        ? permission.getId()
                        : null;

        if (existingPermissionId == null
                ? newPermissionId != null
                : !existingPermissionId.equals(newPermissionId)) {

            menu.setPermission(permission);
            changed = true;
        }

        // =====================================================
        // SAVE CHANGES
        // =====================================================

        if (changed) {

            menu = menuRepository.save(menu);
        }

        return menu;
    }
}