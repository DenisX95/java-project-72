@import hexlet.code.dto.UrlsPage
@import hexlet.code.util.DateFormatter
@import hexlet.code.util.NamedRoutes
@param UrlsPage page

@template.layout.page(
page = page,
content = @`
    <section class="py-5 bg-light">
        <div class="container-xl">
            <div class="bg-white rounded-4 shadow p-4">
                <h1 class="mb-4">Сайты</h1>

                <table class="table table-striped table-hover align-middle">
                    <thead class="table-light">
                        <tr>
                            <th scope="col" class="col-1">ID</th>
                            <th scope="col">Имя</th>
                            <th scope="col" class="col-2">Последняя проверка</th>
                            <th scope="col" class="col-1">Код ответа</th>
                        </tr>
                    </thead>
                    <tbody>
                        @for(var url : page.getUrls())
                            <tr>
                                <td>
                                    ${url.getId()}
                                </td>
                                <td>
                                    <a href="${NamedRoutes.urlPath(url.getId())}" class="text-decoration-none fw-semibold">${url.getName()}</a>
                                </td>
                                <td class="text-muted small">
                                    ${url.getLastCheck() != null ? DateFormatter.formatDate(url.getLastCheck()) : ""}
                                </td>
                                <td class="text-muted small">
                                    ${url.getLastStatusCode() != null ? String.valueOf(url.getLastStatusCode()) : ""}
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