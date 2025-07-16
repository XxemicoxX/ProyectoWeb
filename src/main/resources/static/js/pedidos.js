let contadorProductos = 0;
let contadorExtras = 0;
let totalPedido = 0;

document.addEventListener('DOMContentLoaded', function () {
    // Inicializar el select de producto como deshabilitado
    const productoSelect = document.getElementById('producto');
    productoSelect.disabled = true;
});

// Filtrar productos por categoría
document.getElementById('categoria').addEventListener('change', function () {
    const categoriaId = this.value;
    const productoSelect = document.getElementById('producto');
    const precioInput = document.getElementById('precio');

    // Limpiar campos relacionados
    productoSelect.value = '';
    precioInput.value = '';

    if (categoriaId) {
        // Habilitar el select de productos
        productoSelect.disabled = false;

        // Filtrar productos por categoría
        Array.from(productoSelect.options).forEach(option => {
            if (option.value === '') {
                // Mantener la opción por defecto visible
                option.style.display = 'block';
            } else {
                const productoCategoria = option.getAttribute('data-categoria');
                if (productoCategoria === categoriaId) {
                    option.style.display = 'block';
                } else {
                    option.style.display = 'none';
                }
            }
        });
    } else {
        // Deshabilitar el select de productos si no hay categoría seleccionada
        productoSelect.disabled = true;
    }
});

// Actualizar precio al seleccionar producto
document.getElementById('producto').addEventListener('change', function () {
    const selectedOption = this.options[this.selectedIndex];
    const precio = selectedOption.getAttribute('data-precio');
    document.getElementById('precio').value = precio || '';
});

// Actualizar precio al seleccionar extra
document.getElementById('extra').addEventListener('change', function () {
    const selectedOption = this.options[this.selectedIndex];
    const precio = selectedOption.getAttribute('data-precio');
    document.getElementById('precioExtra').value = precio || '';
});

// Agregar producto
document.getElementById('btnAgregarProducto').addEventListener('click', function () {
    const categoriaSelect = document.getElementById('categoria');
    const productoSelect = document.getElementById('producto');
    const cantidad = parseInt(document.getElementById('cantidad').value);
    const precio = parseFloat(document.getElementById('precio').value);

    if (!categoriaSelect.value || !productoSelect.value || isNaN(cantidad) || cantidad <= 0 || isNaN(precio)) {
        alert("Por favor, completa todos los campos del producto correctamente.");
        return;
    }

    const categoriaNombre = categoriaSelect.options[categoriaSelect.selectedIndex].text;
    const productoNombre = productoSelect.options[productoSelect.selectedIndex].text.split(' - S/. ')[0];
    const subtotal = cantidad * precio;

    agregarFilaDetalle(productoNombre, cantidad, precio, 'Producto', subtotal, 'producto', productoSelect.value);

    // Limpiar formulario de productos
    limpiarFormularioProductos();
});

// Agregar extra
document.getElementById('btnAgregarExtra').addEventListener('click', function () {
    const extraSelect = document.getElementById('extra');
    const cantidad = parseInt(document.getElementById('cantidadExtra').value);
    const precio = parseFloat(document.getElementById('precioExtra').value);

    if (!extraSelect.value || isNaN(cantidad) || cantidad <= 0 || isNaN(precio)) {
        alert("Por favor, completa todos los campos del extra correctamente.");
        return;
    }

    const extraNombre = extraSelect.options[extraSelect.selectedIndex].text.split(' - S/. ')[0];
    const subtotal = cantidad * precio;

    agregarFilaDetalle(extraNombre, cantidad, precio, 'Extra', subtotal, 'extra', extraSelect.value);

    // Limpiar formulario de extras
    limpiarFormularioExtras();
});

function agregarFilaDetalle(nombre, cantidad, precio, tipo, subtotal, tipoElemento, id) {
    const tbody = document.getElementById('detalleBody');
    const fila = tbody.insertRow();

    // Generar ID único para la fila
    const contadorActual = tipoElemento === 'producto' ? contadorProductos : contadorExtras;
    const filaId = `fila-${tipoElemento}-${contadorActual}`;
    fila.id = filaId;

    fila.innerHTML = `
                <td>${nombre}</td>
                <td>${cantidad}</td>
                <td>S/. ${precio.toFixed(2)}</td>
                <td><span class="badge ${tipo === 'Extra' ? 'bg-warning' : 'bg-success'}">${tipo}</span></td>
                <td>S/. ${subtotal.toFixed(2)}</td>
                <td>
                    <button type="button" class="btn btn-danger btn-sm" onclick="eliminarDetalle('${filaId}', ${subtotal}, '${tipoElemento}', ${contadorActual})">
                        <i class="fas fa-trash"></i>
                    </button>
                </td>
            `;

    // Agregar inputs ocultos según el tipo
    if (tipoElemento === 'producto') {
        const div = document.createElement('div');
        div.id = `producto-${contadorProductos}`;
        div.innerHTML = `
                    <input type="hidden" name="detallesProductos[${contadorProductos}].idProducto" value="${id}" />
                    <input type="hidden" name="detallesProductos[${contadorProductos}].cantidad" value="${cantidad}" />
                    <input type="hidden" name="detallesProductos[${contadorProductos}].precio" value="${precio}" />
                    <input type="hidden" name="detallesProductos[${contadorProductos}].subtotal" value="${subtotal}" />
                `;
        document.getElementById('productosHiddenInputs').appendChild(div);
        contadorProductos++;
    } else {
        const div = document.createElement('div');
        div.id = `extra-${contadorExtras}`;
        div.innerHTML = `
                    <input type="hidden" name="detallesExtras[${contadorExtras}].idDetallePedido" value="0" />
                    <input type="hidden" name="detallesExtras[${contadorExtras}].idExtra" value="${id}" />
                    <input type="hidden" name="detallesExtras[${contadorExtras}].cantidad" value="${cantidad}" />
                    <input type="hidden" name="detallesExtras[${contadorExtras}].precio" value="${precio}" />
                    <input type="hidden" name="detallesExtras[${contadorExtras}].subtotal" value="${subtotal}" />
                `;
        document.getElementById('extrasHiddenInputs').appendChild(div);
        contadorExtras++;
    }

    totalPedido += subtotal;
    actualizarTotal();
}

function eliminarDetalle(filaId, subtotal, tipoElemento, index) {
    const fila = document.getElementById(filaId);
    if (fila) {
        fila.remove();
    }

    totalPedido -= subtotal;

    // Eliminar el input oculto correspondiente
    const detalleElement = document.getElementById(`${tipoElemento}-${index}`);
    if (detalleElement) {
        detalleElement.remove();
    }

    actualizarTotal();
}

function actualizarTotal() {
    document.getElementById('totalPedido').textContent = `S/. ${totalPedido.toFixed(2)}`;
}

function limpiarFormularioProductos() {
    document.getElementById('cantidad').value = '';
    document.getElementById('categoria').value = '';
    document.getElementById('producto').value = '';
    document.getElementById('precio').value = '';

    // Deshabilitar el select de productos y restaurar visibilidad
    const productoSelect = document.getElementById('producto');
    productoSelect.disabled = true;

    Array.from(productoSelect.options).forEach(option => {
        option.style.display = 'block';
    });
}

function limpiarFormularioExtras() {
    document.getElementById('cantidadExtra').value = '';
    document.getElementById('extra').value = '';
    document.getElementById('precioExtra').value = '';
}

// Limpiar todo
document.getElementById('btnLimpiar').addEventListener('click', function () {
    if (confirm('¿Estás seguro de que quieres limpiar todos los detalles del pedido?')) {
        document.getElementById('detalleBody').innerHTML = '';
        document.getElementById('productosHiddenInputs').innerHTML = '';
        document.getElementById('extrasHiddenInputs').innerHTML = '';
        totalPedido = 0;
        contadorProductos = 0;
        contadorExtras = 0;
        actualizarTotal();

        // Limpiar también los formularios
        limpiarFormularioProductos();
        limpiarFormularioExtras();
    }
});