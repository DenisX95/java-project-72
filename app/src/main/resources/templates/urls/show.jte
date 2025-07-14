@import hexlet.code.dto.UrlPage
@import hexlet.code.util.DateFormatter
@import hexlet.code.util.NamedRoutes
@param UrlPage page

@template.layout.page(
page = page,
content = @`
    <section class="py-5 bg-light">
        <div class="container-xl">
            <div class="bg-white rounded-4 shadow p-4 mb-5">
                <h1 class="mb-4">${page.getUrl().getName()}</h1>

                <table class="table table-striped align-middle">
                    <tbody>
                    <tr>
                        <th scope="row" class="w-25">ID</th>
                        <td>${page.getUrl().getId()}</td>
                    </tr>
                    <tr>
                        <th scope="row">Имя</th>
                        <td>${page.getUrl().getName()}</td>
                    </tr>
                    <tr>
                        <th scope="row">Дата создания</th>
                        <td class="text-muted small">${DateFormatter.formatDate(page.getUrl().getCreatedAt())}</td>
                    </tr>
                    </tbody>
                </table>
            </div>

            <div class="bg-white rounded-4 shadow p-4">
                <div class="d-flex justify-content-between align-items-center mb-3">
                    <h2 class="mb-0">Проверки</h2>
                    <form method="post" action="${NamedRoutes.urlCheckPath(page.getUrl().getId())}">
                        <button type="submit" class="btn btn-primary">Запустить проверку</button>
                    </form>
                </div>

                <table class="table table-striped table-hover align-middle">
                    <thead>
                        <th scope="col" class="col-1">ID</th>
                        <th scope="col" class="col-1">Код ответа</th>
                        <th scope="col">title</th>
                        <th scope="col">h1</th>
                        <th scope="col">description</th>
                        <th scope="col" class="col-2">Дата проверки</th>
                    </thead>
                    <tbody>
                        @for(var check : page.getChecks())
                            <tr>
                                <td>
                                    ${check.getId()}
                                </td>
                                <td>
                                    ${check.getStatusCode()}
                                </td>
                                <td>
                                    ${check.getTitle()}
                                </td>
                                <td>
                                    ${check.getH1()}
                                </td>
                                <td>
                                    ${check.getDescription()}
                                </td>
                                <td class="text-muted small">
                                    ${DateFormatter.formatDate(check.getCreatedAt())}
                                </td>
                            </tr>
                        @endfor
                    </tbody>
                </table>
            </div>
        </div>
    </section>
`
)