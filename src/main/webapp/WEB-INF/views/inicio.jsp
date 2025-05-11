<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="includes/head.jsp"/>
</head>
<body>
<jsp:include page="includes/header.jsp" />
  <main>
    <div id="myCarousel" class="carousel slide mb-4" data-bs-ride="carousel">
      <div class="carousel-indicators">
        <button type="button" data-bs-target="#myCarousel" data-bs-slide-to="0" class="active" aria-current="true"
          aria-label="Slide 1"></button>
        <button type="button" data-bs-target="#myCarousel" data-bs-slide-to="1" aria-label="Slide 2"></button>
        <button type="button" data-bs-target="#myCarousel" data-bs-slide-to="2" aria-label="Slide 3"></button>
      </div>
      <div class="carousel-inner">
        <div class="carousel-item active">
          <img src="img/slider.png" alt="Descripción de la imagen"  style="width: 100%;height: auto;">
          <div class="container">
            <div class="carousel-caption text-start">
              <h1>PANADERIA DON RODRIGUEZ</h1>
              <p class="opacity-75">En <strong>Panadería DON RODRIGUEZ,</strong>,
                cada bocado cuenta una historia de tradición y calidad.</p>
              <p><a class="btn btn-lg" href="#">COMPRAR</a></p>
            </div>
          </div>
        </div>
        <div class="carousel-item ">
          <img src="img/slider.png" alt="Descripción de la imagen"  class="img-fluid">
          <div class="container">
            <div class="carousel-caption text-start">
              <h1>PANADERIA DON RODRIGUEZ</h1>
              <p class="opacity-75">En <strong>Panadería DON RODRIGUEZ,</strong>,
                cada bocado cuenta una historia de tradición y calidad.</p>
              <p><a class="btn btn-lg" href="#">COMPRAR</a></p>
            </div>
          </div>
        </div>
        <div class="carousel-item ">
          <img src="img/slider.png" alt="Descripción de la imagen" class="img-fluid">
          <div class="container">
            <div class="carousel-caption text-start">
              <h1>PANADERIA DON RODRIGUEZ</h1>
              <p class="opacity-75">En <strong>Panadería DON RODRIGUEZ,</strong>,
                cada bocado cuenta una historia de tradición y calidad.</p>
              <p><a class="btn btn-lg" href="#">COMPRAR</a></p>
            </div>
          </div>
        </div>
      </div>
      <button class="carousel-control-prev" type="button" data-bs-target="#myCarousel" data-bs-slide="prev">
        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
        <span class="visually-hidden">Previous</span>
      </button>
      <button class="carousel-control-next" type="button" data-bs-target="#myCarousel" data-bs-slide="next">
        <span class="carousel-control-next-icon" aria-hidden="true"></span>
        <span class="visually-hidden">Next</span>
      </button>
    </div>

    <!--CONTENIDO -->
    <div class="container mb-3">
      <div class="row">
        <div class="col-lg-3 col-6 my-2">
          <div class="card shadow blog">
            <div class="container-tarjeta">
              <i class="fa-solid fa-truck"></i>
              <div class="content-tarjeta">
                <span>Delivery</span>
                <p>En envio</p>
              </div>
            </div>
          </div>
        </div>
        <div class="col-lg-3 col-6 my-2">
          <div class="card shadow blog">
            <div class="container-tarjeta">
              <i class="fa-solid fa-mobile-screen"></i>
              <div class="content-tarjeta">
                <span>Contacto</span>
                <p>968 302 472</p>
              </div>
            </div>
          </div>
        </div>
        <div class="col-lg-3 col-6 my-2">
          <div class="card shadow blog">
            <div class="container-tarjeta">
              <i class="fa-solid fa-gift"></i>
              <div class="content-tarjeta">
                <span>Dedicatoria</span>
                <p>En productos</p>
              </div>
            </div>
          </div>
        </div>
        <div class="col-lg-3 col-6 my-2">
          <div class="card shadow blog">
            <div class="container-tarjeta">
              <i class="fa-solid fa-clock"></i>
              <div class="content-tarjeta text-center">
                <span>Horario</span>
                <p>8:00 am a 9:00 pm</p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!--QUIENES SOMOS --->
    <div class="container my-3">
      <div class="row">
        <div class="col-md-6 " style="display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: center;text-align: justify;">
          <h1 class="text-center">¿QUIENES SOMOS?</h1>
          <p class="">En Panadería Don RODRIGUEZ, ofrecemos una experiencia excepcional con nuestros productos artesanales,
            desde panes frescos hasta dulces y pasteles. Situados en el corazón de [Ciudad/Barrio], nos especializamos
            en panes crujientes y dulces caseros elaborados con ingredientes de alta calidad y recetas tradicionales.
            Nuestro compromiso con la frescura y el sabor se refleja en cada bocado. Además, apoyamos a la comunidad
            trabajando con proveedores locales. Ven a visitarnos y descubre la dedicación y el arte detrás de cada
            creación. ¡Te esperamos con los brazos abiertos! </p>
        </div>
        <div class="col-md-6">
          <img src="img/panaderia.jpg" alt="Imagen panaderia" style="width: 100%;height: auto;">
        </div>
      </div>
    </div>
    <h2>SUSCRIBETE A NUESTROS TALLERES</h2>
    <div class="container">
      <div class="row">
        <div class="col-md-6 col-lg-4 col-12 mt-2">
          <div class="card h-100 shadow">
            <img src="img/PANADERIA BASICA.jpg" class="img-fluid" alt="">
            <h5>TALLER PANADERIA BASICA</h5>
            <div class="table-responsive">
              <ol class="list-group list-group-numbered">
                <li class="list-group-item d-flex justify-content-between align-items-start">
                  <div class="ms-2 me-auto">
                    <div class="fw-bold">¿QUE APRENDERAS?</div>
                    <ul>
                      <li>Técnicas básicas de panadería y pastelería.</li>
                      <li> Preparación y amasado de masas.</li>
                      <li>Elaboración de pan casero.</li>
                      <li>Uso de herramientas y utensilios de panadería.</li>
                    </ul>  
                  </div>    
                </li>
                <li class="list-group-item d-flex justify-content-between align-items-start">
                  <div class="ms-2 me-auto">
                    <div class="fw-bold">HORARIO DE CLASES</div>
                    <ul>
                      <li>Lunes: 10:00 AM - 12:00 PM</li>
                      <li>Viernes: 6:00 PM - 8:00 PM</li>
                    </ul>
                  </div>
               
                </li>
                <li class="list-group-item d-flex justify-content-between align-items-start">
                  <div class="ms-2 me-auto">
                    <div class="fw-bold">UTENSILIOS NECESARIOS</div>
                    <ul>
                      <li>Rodillo</li>
                      <li>Papel para hornear.</li>
                      <li>Tazas y cucharas medidoras</li>
                    </ul>
                  </div>
                </li>
              </ol>
            </div>
            <div class="container text-center">
              <div class="row">
                <p class=" text-warning fw-bold ">GRATUTITO</p>
                <p> <strong>Fecha inicio:</strong> 09-09-2025</p>
                <p> <strong>Fecha Fin:</strong> 15-09-2025</p>
              </div>
            </div>
            <p class="text-center">
              <a href="details.php?id=1&token= bf072c2eadbfadc7cd53cf14a205624f33357ac7" class="btn">SUSCRIBIRSE</a>
            </p>
          </div>
        </div>
        <!---->
        <div class="col-md-6 col-lg-4 col-12 mt-2">
          <div class="card h-100 shadow">
            <img src="img/PANADERIA BASICA.jpg" class="img-fluid" alt="">
            <h5>TALLER PANADERIA INTERMEDIA</h5>
            <div class="table-responsive">
              <ol class="list-group list-group-numbered">
                <li class="list-group-item d-flex justify-content-between align-items-start">
                  <div class="ms-2 me-auto">
                    <div class="fw-bold">¿QUE APRENDERAS?</div>
                    <ul>
                      <li>Técnicas básicas de panadería y pastelería.</li>
                      <li> Preparación y amasado de masas.</li>
                      <li>Elaboración de pan casero.</li>
                      <li>Uso de herramientas y utensilios de panadería.</li>
                    </ul>  
                  </div>    
                </li>
                <li class="list-group-item d-flex justify-content-between align-items-start">
                  <div class="ms-2 me-auto">
                    <div class="fw-bold">HORARIO DE CLASES</div>
                    <ul>
                      <li>Lunes: 10:00 AM - 12:00 PM</li>
                      <li>Viernes: 6:00 PM - 8:00 PM</li>
                    </ul>
                  </div>
               
                </li>
                <li class="list-group-item d-flex justify-content-between align-items-start">
                  <div class="ms-2 me-auto">
                    <div class="fw-bold">UTENSILIOS NECESARIOS</div>
                    <ul>
                      <li>Rodillo</li>
                      <li>Papel para hornear.</li>
                      <li>Tazas y cucharas medidoras</li>
                    </ul>
                  </div>
                </li>
              </ol>
            </div>
            <div class="container text-center">
              <div class="row">
                <p class=" text-warning fw-bold "> S/ 100</p>
                <p> <strong>Fecha inicio:</strong> 09-09-2025</p>
                <p> <strong>Fecha Fin:</strong> 15-09-2025</p>
              </div>
            </div>
            <p class="text-center">
              <a href="details.php?id=1&token= bf072c2eadbfadc7cd53cf14a205624f33357ac7" class="btn">SUSCRIBIRSE</a>
            </p>
          </div>
        </div>
        <!---->
        <div class="col-md-6 col-lg-4 col-12 mt-2">
          <div class="card h-100 shadow">
            <img src="img/PANADERIA BASICA.jpg" class="img-fluid" alt="">
            <h5>TALLER PANADERIA EXPERTA</h5>
            <div class="table-responsive">
              <ol class="list-group list-group-numbered">
                <li class="list-group-item d-flex justify-content-between align-items-start">
                  <div class="ms-2 me-auto">
                    <div class="fw-bold">¿QUE APRENDERAS?</div>
                    <ul>
                      <li>Técnicas básicas de panadería y pastelería.</li>
                      <li> Preparación y amasado de masas.</li>
                      <li>Elaboración de pan casero.</li>
                      <li>Uso de herramientas y utensilios de panadería.</li>
                    </ul>  
                  </div>    
                </li>
                <li class="list-group-item d-flex justify-content-between align-items-start">
                  <div class="ms-2 me-auto">
                    <div class="fw-bold">HORARIO DE CLASES</div>
                    <ul>
                      <li>Lunes: 10:00 AM - 12:00 PM</li>
                      <li>Viernes: 6:00 PM - 8:00 PM</li>
                    </ul>
                  </div>
               
                </li>
                <li class="list-group-item d-flex justify-content-between align-items-start">
                  <div class="ms-2 me-auto">
                    <div class="fw-bold">UTENSILIOS NECESARIOS</div>
                    <ul>
                      <li>Rodillo</li>
                      <li>Papel para hornear.</li>
                      <li>Tazas y cucharas medidoras</li>
                    </ul>
                  </div>
                </li>
              </ol>
            </div>
            <div class="container text-center">
              <div class="row">
                <p class=" text-warning fw-bold ">S/ 230</p>
                <p> <strong>Fecha inicio:</strong> 09-09-2025</p>
                <p> <strong>Fecha Fin:</strong> 15-09-2025</p>
              </div>
            </div>
            <p class="text-center">
              <a href="details.php?id=1&token= bf072c2eadbfadc7cd53cf14a205624f33357ac7" class="btn">SUSCRIBIRSE</a>
            </p>
          </div>
        </div>
      </div>
    </div> 
    <!--UBICANOS-->
    <div class="container my-3">
      <div class="row">
        <h2>UBICANOS AQUI</h2>
        <iframe src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3903.101625899168!2d-77.0076054239995!3d-11.967465340431339!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x9105c55e6be23b3f%3A0xf062063e5fad4d48!2sKFC!5e0!3m2!1ses-419!2spe!4v1725214764380!5m2!1ses-419!2spe" width="600" height="450" style="border:0;" allowfullscreen="" loading="lazy" referrerpolicy="no-referrer-when-downgrade"></iframe>
      </div>
    </div>
<!--CONTACTO-->
<div class="container my-3">
  <div class="row justify-content-md-center">
    <h2>CONTACTO</h2>
    <div class="col-lg-8">
      <div class="form-floating mb-3 ">
        <input type="email" class="form-control" id="floatingInput" placeholder="name@example.com">
        <label for="floatingInput">Nombres y Apellidos</label>
      </div>
      <div class="row">
        <div class="col-6">
          <div class="form-floating">
            <input type="password" class="form-control" id="floatingPassword1" placeholder="Password">
            <label for="floatingPassword1">Correo Electronico</label>
          </div>
        </div>
        <div class="col-6">
          <div class="form-floating">
            <input type="password" class="form-control" id="floatingPassword2" placeholder="Password">
            <label for="floatingPassword2">Telefono</label>
          </div>
        </div>
        <div class="col-12 mt-3">
          <div class="form-floating">
            <textarea class="form-control" placeholder="Leave a comment here" id="floatingTextarea2" style="height: 130px;resize: none;"></textarea>
            <label for="floatingTextarea2">Mensaje</label>
          </div>
        </div>  
      </div>
    </div>
  </div>
</div>
  </main>
<jsp:include page="includes/footer.jsp" />
</body>
</html>