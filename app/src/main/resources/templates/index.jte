@import hexlet.code.dto.BasePage
@param BasePage page

@template.layout.page(
page = page,
content = @`
    <section class="d-flex align-items-center justify-content-center min-vh-100 bg-light">
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-md-10 col-lg-6 text-center bg-white p-5 rounded-4 shadow">
                    <h1 class="display-5 fw-semibold mb-3">Анализатор страниц</h1>
                    <p class="lead text-muted mb-4">Бесплатно проверяйте сайты на SEO пригодность</p>
                    <form action="/urls" method="post" class="row g-2 justify-content-center">
                        <div class="col-12">
                            <div class="form-floating">
                                <input id="url-input" autofocus type="text" name="url" required class="form-control" placeholder="Ссылка" autocomplete="off">
                                <label for="url-input">Ссылка</label>
                            </div>
                        </div>
                        <div class="col-auto">
                            <button type="submit" class="btn btn-primary btn-lg px-4">Проверить</button>
                        </div>
                    </form>
                    <p class="text-muted mt-3 small">Пример: https://www.example.com</p>
                </div>
            </div>
        </div>
    </section>
`
)