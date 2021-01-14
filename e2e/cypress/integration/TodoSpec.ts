context('todo', () => {
    beforeEach(() => {
        cy.visit("http://localhost:3000")
    })

    it('submits a new todo', () => {
        cy.get('input[type="text"]')
            .type("this is a new todo.").should('have.value', "this is a new todo.")
        cy.get('button').contains("Submit").click()
        cy.get('.TodoDetail').first().findByText("this is a new todo.")
    })

    it('finishes a todo', () => {
        cy.get('input[type="checkbox"]').first().should("not.be.checked")
        cy.get('input[type="checkbox"]').first().check()
        cy.reload()
        cy.get('input[type="checkbox"]').first().should("be.checked")
    })

    it('finishes a todo', () => {
        let todoLen_before: number
        cy.get('.TodoDetail').then((todoList) => {
            todoLen_before = Cypress.$(todoList).length
        })

        cy.get('.todoDeleteButton').first().click()
        cy.reload()
        cy.get('.TodoDetail').should((todoList_after) => {
            const todoLen_after = Cypress.$(todoList_after).length
            expect(todoLen_after).to.equal(todoLen_before - 1)
        })
    })
})