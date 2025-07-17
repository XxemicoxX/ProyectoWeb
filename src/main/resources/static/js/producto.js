document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('form-producto');
    const csrfToken = document.querySelector("meta[name='_csrf']").getAttribute("content");
    const csrfHeader = document.querySelector("meta[name='_csrf_header']").getAttribute("content");

    if (form) {
        form.addEventListener('submit', async (e) => {
            e.preventDefault();

            const formData = new FormData(form);
            const producto = {
                idProducto: formData.get("idProducto") || null,
                nombre: formData.get("nombre").trim(),
                categoria: {
                    idCategoria: formData.get("id_categoria")
                },
                precio: parseFloat(formData.get("precio")) || 0,
                medida: formData.get("medida").trim(),
                imagen: formData.get("imagen").trim(),
                descripcion: formData.get("descripcion").trim()
            };

            try {
                const response = await fetch("/admin/api/productos/save", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                        [csrfHeader]: csrfToken
                    },
                    body: JSON.stringify(producto)
                });

                if (!response.ok) {
                    const errors = await response.json();
                    if (Array.isArray(errors)) {
                        const mensajes = errors.map(e => e.defaultMessage).join("\n");
                        alert("Errores de validación:\n" + mensajes);
                    } else {
                        throw new Error("Error inesperado del servidor");
                    }
                    return;
                }

                alert("✅ Producto guardado correctamente.");
                form.reset(); // Limpia formulario
                window.location.reload(); // Refresca lista

            } catch (error) {
                console.error("Error al guardar:", error);
                alert("❌ Error al guardar el producto.");
            }
        });
    }

    // Botones editar
    document.querySelectorAll('.btn-editar').forEach(btn => {
        btn.addEventListener('click', () => {
            const id = btn.getAttribute('data-id');
            editarProducto(id);
        });
    });

    async function editarProducto(id) {
        try {
            const response = await fetch(`/admin/api/productos/edit/${id}`, {
                headers: {
                    [csrfHeader]: csrfToken
                }
            });

            if (!response.ok) throw new Error("Producto no encontrado");

            const producto = await response.json();

            // Rellenar el formulario
            form.querySelector('[name="idProducto"]').value = producto.idProducto || '';
            form.querySelector('[name="nombre"]').value = producto.nombre || '';
            form.querySelector('[name="id_categoria"]').value = producto.categoria?.idCategoria || '';
            form.querySelector('[name="precio"]').value = producto.precio || '';
            form.querySelector('[name="medida"]').value = producto.medida || '';
            form.querySelector('[name="imagen"]').value = producto.imagen || '';
            form.querySelector('[name="descripcion"]').value = producto.descripcion || '';

            form.scrollIntoView({ behavior: 'smooth' });

        } catch (error) {
            console.error("Error al cargar producto:", error);
            alert("❌ No se pudo cargar el producto para editar.");
        }
    }
});
